package com.example.academy_tbc.presentation.screen.location.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class LocationUi(
    val id: Int,
    val locationTitle: String,
    val locationDescription: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String,
) : ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }

    override fun getTitle(): String {
        return locationTitle
    }

    override fun getSnippet(): String {
        return locationDescription
    }

    override fun getZIndex(): Float? {
        return null
    }
}