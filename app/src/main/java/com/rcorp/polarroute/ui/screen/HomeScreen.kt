package com.rcorp.polarroute.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rcorp.polarroute.R
import com.rcorp.polarroute.data.local.AppPreferences
import com.rcorp.polarroute.model.GPSData
import com.rcorp.polarroute.model.getTotalDistance
import com.rcorp.polarroute.model.toGPSPoints
import com.rcorp.polarroute.ui.component.*
import com.rcorp.polarroute.viewmodel.GoogleMapViewModel
import com.rcorp.polarroute.viewmodel.UploadViewModel
import org.koin.java.KoinJavaComponent

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier.fillMaxSize()) {

    val mapViewModel: GoogleMapViewModel = viewModel()
    val uploadViewModel: UploadViewModel = viewModel()
    val appPreferences = KoinJavaComponent.get<AppPreferences>(AppPreferences::class.java)
    var settingsDialogOpenState by remember {
        mutableStateOf(appPreferences.token.isNullOrEmpty())
    }
    var uploadDialogOpenState by remember {
        mutableStateOf(false)
    }
    val uploadProcessState by uploadViewModel.progress.observeAsState()
    val uploadStatus by uploadViewModel.status.observeAsState()
    val totalDistance by mapViewModel.totalDistance.observeAsState()

    Box(modifier) {
        if (uploadStatus != null)
            uploadDialogOpenState = false
        GoogleMapView(mapViewModel, modifier = Modifier.fillMaxSize())
        Card(
            shape = RoundedCornerShape(corner = CornerSize(20.dp)),
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .align(
                    Alignment.BottomStart
                )
                .padding(bottom = 30.dp, start = 30.dp)
        ) {
            Text(
                text = "${((totalDistance ?: 0.0) / 1000.0).toInt()} km",
                style = MaterialTheme.typography.titleLarge.copy(Color.Black, fontSize = 25.sp),
                modifier = Modifier
                    .align(
                        Alignment.Center
                    ).padding(10.dp)

            )
        }
        SettingsDialog(settingsDialogOpenState) {
            settingsDialogOpenState = false
        }
        UploadDialog(uploadDialogOpenState, uploadProcessState ?: false, {
            uploadDialogOpenState = false
        }) {
            val gpsPoints = mapViewModel.markers.value?.toGPSPoints() ?: listOf()
            uploadViewModel.uploadData(GPSData(gpsPoints, it, gpsPoints.getTotalDistance()))
        }
        ErrorDialog(openState = uploadStatus == UploadViewModel.Status.FAIL) {
            uploadViewModel.status.value = null
        }
        SuccessDialog(openState = uploadStatus == UploadViewModel.Status.SUCCESS) {
            uploadViewModel.status.value = null
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 20.dp, top = 20.dp),
            onClick = {
                settingsDialogOpenState = true
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_settings_24),
                contentDescription = "Settings"
            )
        }
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 70.dp, bottom = 30.dp),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_cloud_upload_24),
                    contentDescription = "Upload"
                )
            },
            text = {
                Text(text = "Upload")
            },
            onClick = {
                uploadDialogOpenState = true
            }
        )
    }
}