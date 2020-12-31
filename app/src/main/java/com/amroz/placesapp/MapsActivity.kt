package com.amroz.placesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
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
        var info_show=findViewById(R.id.info_show) as TextView

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
            var newlocation=LatLng(15.362812860935936, 44.20591257512569)
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            mMap.addMarker(MarkerOptions().position(newlocation).title("NON"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newlocation, 12F))
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
}