package com.rcorp.polarroute.model

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState

data class GPSPoint(
    val latitude: Double, val longitude: Double, val altitude: Double = 0.0,
    var distance: Double
) {

    constructor(latLng: LatLng) : this(latLng.latitude, latLng.longitude, 0.0, 0.0)

    fun toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }

    fun getDistanceBetween(point: GPSPoint): Double {
        val results = FloatArray(1)
        Location.distanceBetween(
            this.latitude,
            this.longitude,
            point.latitude,
            point.longitude,
            results
        )
        return results[0].toDouble()
    }
}

fun MarkerState.toGPSPoint(): GPSPoint {
    return GPSPoint(this.position)
}

fun List<MarkerState>.toGPSPoints(): List<GPSPoint> {
    val points = mutableListOf<GPSPoint>()
    var lastPoint: GPSPoint? = null
    this.forEach {
        val newPoint = it.toGPSPoint()
        if (lastPoint != null)
            newPoint.distance = newPoint.getDistanceBetween(lastPoint!!)
        points.add(newPoint)
        lastPoint = newPoint
    }
    return points
}

fun List<GPSPoint>.getTotalDistance(): Double {
    var distance = 0.0
    var lastPoint: GPSPoint? = null
    this.forEach {
        if (lastPoint != null)
            distance += it.getDistanceBetween(lastPoint!!)
        lastPoint = it
    }
    return distance
}
