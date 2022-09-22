package com.rcorp.polarroute.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.maps.android.compose.MarkerState
import com.rcorp.polarroute.model.GPSPoint
import com.rcorp.polarroute.model.getTotalDistance
import com.rcorp.polarroute.model.toGPSPoints

class GoogleMapViewModel : ViewModel() {

    private val _markers = MutableLiveData<MutableList<MarkerState>>(mutableListOf())
    val markers: LiveData<List<MarkerState>> = Transformations.map(_markers) {
        it
    }
    val totalDistance = MutableLiveData(0.0)

    fun onMapClick(position: MarkerState) {
        _markers.value = mutableListOf<MarkerState>().apply {
            this.addAll(_markers.value ?: mutableListOf())
            this.add(position)
            totalDistance.value = this.toGPSPoints().getTotalDistance()
        }
    }

    fun onMarkerClick(position: MarkerState) {
        _markers.value = mutableListOf<MarkerState>().apply {
            this.addAll(_markers.value ?: mutableListOf())
            this.remove(position)
            totalDistance.value = this.toGPSPoints().getTotalDistance()
        }
    }

    fun computeDistance() {
        totalDistance.value = _markers.value?.toGPSPoints()?.getTotalDistance() ?: 0.0
    }

}