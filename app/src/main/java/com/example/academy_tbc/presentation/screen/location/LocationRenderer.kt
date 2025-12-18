package com.example.academy_tbc.presentation.screen.location

import android.content.Context
import com.example.academy_tbc.presentation.screen.location.model.LocationUi
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
class LocationRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<LocationUi>
) : DefaultClusterRenderer<LocationUi>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: LocationUi, markerOptions: MarkerOptions) {
        markerOptions.title(item.title).position(LatLng(item.latitude, item.longitude))

    }

    override fun onClusterItemRendered(clusterItem: LocationUi, marker: Marker) {
        marker.tag = clusterItem
    }
}