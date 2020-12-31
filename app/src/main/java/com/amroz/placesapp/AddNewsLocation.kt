package com.amroz.placesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_news_location.*
import kotlinx.android.synthetic.main.fragment_contact.*


const val REQUEST_MAP=22
class AddNewsLocation : AppCompatActivity() {
    lateinit var et_title:EditText
    lateinit var et_det:EditText
    lateinit var et_lat:EditText
    lateinit var et_lang:EditText
    lateinit var save:Button
    lateinit var add_location:TextView

    private val ViewModel by lazy {
        ViewModelProviders.of(this).get(ViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news_location)
        var  type:Spinner=findViewById(R.id.type)
        var  save:Button=findViewById(R.id.ed_save)
        et_title=findViewById(R.id.ed_title) as EditText
        et_det=findViewById(R.id.ed_det) as EditText
        et_lat=findViewById(R.id.ed_latitude) as EditText
        et_lang=findViewById(R.id.ed_longitude) as EditText
        save=findViewById(R.id.ed_save) as Button
        add_location=findViewById(R.id.add_location) as TextView


        add_location.setOnClickListener {

            var intint = Intent(this,MapsActivity::class.java)
            var map=2.toString()
            intint.putExtra("Activity",map)

            startActivityForResult(intint, REQUEST_MAP)

        }
        save.setOnClickListener {

            addNewLocation()



        }



        var cats=ArrayList<String>()

        cats.add("Hospitals")
        cats.add("Schools")
        cats.add("Restaurants")
        cats.add("Cafes")
        cats.add("Super Market")


        var adpt= ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cats)
        type.adapter=adpt
    }



fun  addNewLocation(){




    if (et_title.text.toString().trim().length <3){

        et_title.setBackgroundResource(R.drawable.erorrshape)
        Toast.makeText(this,"title must be more than 2",Toast.LENGTH_LONG).show()
    }else

        if (et_det.text.toString().trim().length <3){

            et_det.setBackgroundResource(R.drawable.erorrshape)
            Toast.makeText(this,"Small description",Toast.LENGTH_LONG).show()
        }
    else
            if (et_lat.text.toString().trim().length <2){

                et_lat.setBackgroundResource(R.drawable.erorrshape)
                Toast.makeText(this,"Enter a valid  latitude",Toast.LENGTH_LONG).show()
            }
    else
                if (et_lang.text.toString().trim().length <2){

                    et_lang.setBackgroundResource(R.drawable.erorrshape)
                    Toast.makeText(this,"Enter a valid latitude",Toast.LENGTH_LONG).show()
                }
    else{
                    var data=Places(0,et_title.text.toString(),
                        et_det.text.toString(),
                        et_lat.text.toString().toFloat(),
                        et_lang.text.toString().toFloat(),
                        type.selectedItem.toString())
                         ViewModel.addtask(data)

                    var intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)

                }






}


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode!=1){

            if (resultCode== Activity.RESULT_OK){
                var location=data!!.getSerializableExtra("location").toString()

                val latlong = location!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val latitude = java.lang.Double.parseDouble(latlong[0])
                val longitude = java.lang.Double.parseDouble(latlong[1])
                et_lat.setText(latitude.toString())
                et_lang.setText(longitude.toString())
                Log.d("location111",location)
            }
}
    }
}