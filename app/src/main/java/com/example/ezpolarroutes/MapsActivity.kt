package com.example.ezpolarroutes

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.ezpolarroutes.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var pointList = mutableListOf<LatLng>()
    var totalDisance = 0.0f
    private lateinit var path: Polyline
    var webClient = WebClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.creds.setOnClickListener {
            val credsDialog = CredsDialog(this)
            credsDialog.show()
        }
        binding.upload.setOnClickListener {
            val uploadDialog = UploadDialog(this)
            uploadDialog.show()
        }
        webClient.login(AppPreferences(this).username, AppPreferences(this).password)
            .subscribe({
                Log.i("tag","Ca marche bien !" + it.toString())
            },
            {
                Log.e("tag","ca marche pas...$it", it)
            })

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val stGeorges = LatLng(46.93, -1.32)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stGeorges))
        // adding on click listener to marker of google maps.
        // adding on click listener to marker of google maps.

        mMap.setOnMapClickListener( GoogleMap.OnMapClickListener {
            Log.d("tag","toto")
        })

        path = mMap.addPolyline(PolylineOptions()
            .color(Color.BLUE)
            .width(7f)
            .visible(true)
            .startCap(RoundCap())
            .jointType(JointType.BEVEL)
        )
        mMap.setOnMarkerClickListener {
            for (coord in pointList) {
                if (coord == it.position) {
                    pointList.remove(coord)
                    path.points = pointList
                    break
                }
            }
            it.remove()
            computeTotalDistance()
            true
        }
        mMap.setOnMapLongClickListener( GoogleMap.OnMapLongClickListener {
            Log.d("tag","Long Click")
            mMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("New point")
            )
            pointList.add(it)
            path.points = pointList
            computeTotalDistance()
        })

    }

    fun uploadTheCurrentGPSTrace(name:String) {
        var GPSPointList = mutableListOf<GPSPoint>()
        var lastPoint:LatLng? = null

        pointList.forEach{
            var distance = 0.0
            if (lastPoint != null){
                val results = FloatArray(1)
                Location.distanceBetween(
                    lastPoint?.latitude?:0.0,
                    lastPoint?.longitude?:0.0,
                    it.latitude,
                    it.longitude,
                    results)
                distance = results[0].toDouble()
            }
            Log.i("toto", GPSPointList.toString())
            lastPoint = it
            GPSPointList.add(GPSPoint(it.latitude, it.longitude, 0.0, distance))
        }
        var toto = GPSData(GPSPointList, name,  totalDisance.toDouble())
        webClient.upload(toto).subscribe({
            Log.i("tag","Ca UPLOAD bien !" + it.toString())
        },
            {
                Log.e("tag","ca UPLOAD pas...$it", it)
            })
    }

    fun computeTotalDistance(){
        var prev_coord = LatLng(0.0, 0.0)
        totalDisance = 0.0f
        for (coord in pointList) {
            if (prev_coord.latitude == 0.0 && prev_coord.longitude == 0.0) {
                prev_coord = coord
            }
            else {
                val results = FloatArray(1)
                Location.distanceBetween(
                    prev_coord.latitude,
                    prev_coord.longitude,
                    coord.latitude,
                    coord.longitude,
                    results)
                totalDisance += results[0]
            }
        }
    }
}