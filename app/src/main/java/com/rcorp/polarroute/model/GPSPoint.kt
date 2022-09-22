package com.rcorp.polarroute.model

import com.google.android.gms.maps.model.LatLng

data class GPSPoint(val latitude: Double, val longitude: Double,val altitude:Double = 0.0,
                    val distance:Double) {

    constructor(latLng: LatLng) : this(latLng.latitude, latLng.longitude, 0.0, 0.0)

    fun toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }
}
