package com.example.academy_tbc.presentation.screen.location

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentMapBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.location.model.LocationUi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback {
    private var cameraPosition: CameraPosition? = null
    private var lastKnownLocation: Location? = null
    private lateinit var googleMap: GoogleMap
    private val mapViewModel: MapViewModel by viewModels()

    private lateinit var clusterManager: ClusterManager<LocationUi>

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            lastKnownLocation = it.getParcelable(KEY_LOCATION)
            cameraPosition = it.getParcelable(KEY_CAMERA_POSITION)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        requestPermissions()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (::googleMap.isInitialized) {
            outState.putParcelable(KEY_CAMERA_POSITION, googleMap.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
    }


    override fun init(savedInstanceState: Bundle?) {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapViewModel.onEvent(MapEvent.GetLocation)
        observeMapSideEffect()
        onShowAllMarkersButtonClick()
    }


    private fun requestPermissions() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val granted = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
            if (granted) {
                checkLocationEnabled()
            } else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showPermissionRequiredDialog()
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun showPermissionRequiredDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.location_permission_is_needed))
            .setMessage(getString(R.string.location_permission_is_needed))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.exit)) { _, _ ->
                requireActivity().finish()
            }
            .show()
    }

    private fun checkLocationEnabled() {
        if (!isLocationEnabled(requireContext())) {
            binding.root.showSnackBar(getString(com.google.android.gms.base.R.string.common_google_play_services_enable_title))
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            locationManager.isLocationEnabled
        } else {
            val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            gpsEnabled || networkEnabled
        }
    }

    private fun observeMapState() {
        lifecycleCollectLatest(mapViewModel.state) { state ->
            binding.progressBar.isVisible = state.isLoading
            updateMapWithLocations(state.locations)
            binding.btnShowAllMarkers.isEnabled = state.locations.isNotEmpty()
        }
    }

    private fun observeMapSideEffect() {
        lifecycleCollectLatest(mapViewModel.effect) { effect ->
            when (effect) {
                is MapSideEffect.ShowError -> {}
                is MapSideEffect.ZoomToBounds -> { zoomAll(effect.items) }
            }

        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map


        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
        }
        clusterManager = ClusterManager(requireContext(), googleMap)
        clusterManager.renderer = LocationRenderer(requireContext(), googleMap, clusterManager)

        googleMap.setOnCameraIdleListener(clusterManager)
        clusterManager.setOnClusterItemClickListener {
            findNavController().navigate(
                MapFragmentDirections.actionMapFragmentToMarkerBottomSheetFragment(
                    title = it.locationTitle,
                    address = it.locationDescription,
                    image = it.imageUrl
                )
            )
            true
        }

        observeMapState()
        enableMyLocation()
        getDeviceLocation()
    }

    private fun enableMyLocation() {
        if (requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            requireContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        }
    }

    private fun getDeviceLocation() {
        try {
            if (requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                requireContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {

                fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            val latLng = LatLng(location.latitude, location.longitude)
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(latLng)
                                    .title(getString(R.string.your_location))
                                    .icon(
                                        BitmapDescriptorFactory.defaultMarker(
                                            BitmapDescriptorFactory.HUE_BLUE
                                        )
                                    )
                            )
                            cameraPosition?.let {
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                            }
                        } else {
                            binding.root.showSnackBar(getString(R.string.unable_to_get_location))
                        }
                    }
                    .addOnFailureListener {
                        binding.root.showSnackBar(getString(R.string.unable_to_get_location))
                    }
            }
        } catch (_: SecurityException) {
            Snackbar.make(
                binding.root,
                getString(R.string.location_permission_is_needed), Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun updateMapWithLocations(locations: List<LocationUi>) {
        clusterManager.clearItems()
        clusterManager.addItems(locations)
        clusterManager.cluster()
    }

    private fun onShowAllMarkersButtonClick() {
        binding.btnShowAllMarkers.setOnClickListener {
            mapViewModel.onEvent(MapEvent.ShowAllMarkers)
        }
    }


    private fun zoomAll(items: List<LocationUi>) {
        val builder = LatLngBounds.builder()
        items.forEach { builder.include(LatLng(it.latitude, it.longitude)) }

        googleMap.setOnMapLoadedCallback {
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(builder.build(), 120)
            )
        }
    }

    companion object {
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
}