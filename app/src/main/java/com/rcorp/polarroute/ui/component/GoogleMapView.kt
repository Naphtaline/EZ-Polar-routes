package com.rcorp.polarroute.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.rcorp.polarroute.model.GPSPoint
import com.rcorp.polarroute.viewmodel.GoogleMapViewModel

@Composable
fun GoogleMapView(mapViewModel: GoogleMapViewModel, modifier: Modifier = Modifier.fillMaxSize()) {


    val startPos = LatLng(50.522362465744365, 8.401691494731605)

    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.HYBRID))
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPos, 6f)
    }

    val markers by mapViewModel.markers.observeAsState()


    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings,
        onMapClick = {
            mapViewModel.onMapClick(MarkerState(position = it))
        }
    ) {
        mapViewModel.computeDistance()
        var lastLatLng: MarkerState? = null
        markers?.forEach { markerState ->
            if (lastLatLng != null)
                Polyline(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    points = mutableListOf(lastLatLng!!.position, markerState.position)
                )
            Marker(
                draggable = true,
                state = markerState,
                onClick = {
                    mapViewModel.onMarkerClick(markerState)
                    true
                }
            )
            lastLatLng = markerState
        }
    }

}