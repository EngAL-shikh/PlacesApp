package com.amroz.placesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.place_list.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val ViewModel by lazy {
        ViewModelProviders.of(this).get(ViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)




    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap




        var catList= intent.getSerializableExtra("places") as CatList





       for (place in catList.items ){

           val sanaa = LatLng(place.latitude.toDouble(),place.longitude.toDouble())


           Log.d("lang",place.latitude.toString())
           mMap.addMarker(MarkerOptions().position(sanaa).title(place.Describe).snippet(place.title))
           mMap.animateCamera(CameraUpdateFactory.newLatLng(sanaa))
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sanaa, 14F))

       }










    }
}