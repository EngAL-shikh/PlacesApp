package com.amroz.placesapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


import kotlinx.android.synthetic.main.place_list.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1010
    private lateinit var mMap: GoogleMap
    private val ViewModel by lazy {
        ViewModelProviders.of(this).get(ViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        var getRaylLocation=findViewById(R.id.getRaylLocation) as ImageView
        var info_show=findViewById(R.id.info_show) as TextView

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getRaylLocation.setOnClickListener {
            Log.d("Debug:",CheckPermission().toString())
            Log.d("Debug:",isLocationEnabled().toString())
            RequestPermission()
            getLastLocation()
        }




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        info_show.setOnClickListener {
            var intent=Intent(this,MainActivity::class.java)
             startActivity(intent)
        }




    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var getLocation =intent.extras!!.getString("Activity").toString()





        if (getLocation =="1"){

            // get single location
            var singlePlace= intent.getSerializableExtra("singleplace") as Places
            val sanaa = LatLng(singlePlace.latitude.toDouble(),singlePlace.longitude.toDouble())

            mMap.addMarker(MarkerOptions().position(sanaa).title(singlePlace.title).snippet(singlePlace.Describe))
            mMap.animateCamera(CameraUpdateFactory.newLatLng(sanaa))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sanaa, 14F))

        }else if (getLocation=="2"){

            // get latitude and longitude frome the map
            var newlocation=LatLng(15.364015194891525,44.20692175626755)

            mMap.addMarker(MarkerOptions().position(newlocation).title("NON"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newlocation, 17F))
            mMap.setOnMapClickListener(object :GoogleMap.OnMapClickListener {
                override fun onMapClick(latlng :LatLng) {
                    val location1 = LatLng(latlng.latitude,latlng.longitude)
                    var location=latlng.latitude.toString()+","+latlng.longitude.toString()
                    var loc=Intent()
                    loc.putExtra("location",location.toString())
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(location1))
                    setResult(Activity.RESULT_OK,loc)
                    finish()
                }
            })

        }else if (getLocation=="0"){

            // get all locations

                var catList= intent.getSerializableExtra("places") as CatList

                for (place in catList.items ){
                    val sanaa = LatLng(place.latitude.toDouble(),place.longitude.toDouble())
                    Log.d("lang",place.latitude.toString())
                    mMap.addMarker(MarkerOptions().position(sanaa).title(place.title).snippet(place.Describe))
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(sanaa))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sanaa, 14F))
                }
            }
















    }



    // get my location


    // fun for Checking the Permission
    fun CheckPermission():Boolean{
        if(
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }


    //fun for  RequestPermission

    fun RequestPermission(){
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }


    //fun for checking the location service is enabled

    fun isLocationEnabled():Boolean{

        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    // fun get last location

    fun getLastLocation(){
        if(CheckPermission()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location: Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        val sanaa = LatLng(location.latitude,location.longitude)

                        mMap.addMarker(MarkerOptions().position(sanaa))
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(sanaa))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sanaa, 16F))
                        Toast.makeText(this,"You Current Location is : Long: "+ location.longitude + " , Lat: " + location.latitude + "\n",Toast.LENGTH_SHORT).show()

                    }
                }
            }else{
                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }


    // fun get new location

    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
            //textView.text = "You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" + getCityName(lastLocation.latitude,lastLocation.longitude)
        }
    }
    private fun getCityName(lat: Double,long: Double):String{
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)

        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        Log.d("Debug:","Your City: " + cityName + " ; your Country " + countryName)
        return cityName
    }




}