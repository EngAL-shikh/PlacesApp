package com.amroz.placesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_news_location.*
import kotlinx.android.synthetic.main.fragment_contact.*

class AddNewsLocation : AppCompatActivity() {
    lateinit var et_title:EditText
    lateinit var et_det:EditText
    lateinit var et_lat:EditText
    lateinit var et_lang:EditText
    lateinit var save:Button

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