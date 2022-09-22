package com.rcorp.polarroute.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rcorp.polarroute.model.GPSPoint

class GoogleMapViewModel : ViewModel() {

    private val _markers = MutableLiveData<MutableList<GPSPoint>>(mutableListOf())
    val markers: LiveData<List<GPSPoint>> = Transformations.map(_markers) {
        it
    }

    fun onMapClick(position: GPSPoint) {
        _markers.value = mutableListOf<GPSPoint>().apply {
            this.addAll(_markers.value ?: mutableListOf())
            this.add(position)
        }
    }

    fun onMarkerClick(position: GPSPoint) {
        _markers.value = mutableListOf<GPSPoint>().apply {
            this.addAll(_markers.value ?: mutableListOf())
            this.remove(position)
        }
    }

}