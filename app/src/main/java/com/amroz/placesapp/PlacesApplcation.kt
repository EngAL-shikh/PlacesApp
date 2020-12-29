package com.amroz.placesapp

import android.app.Application

class PlacesApplcation: Application() {

    override fun onCreate() {
        super.onCreate()
        PlacesRepository.initialize(this)
    }

}