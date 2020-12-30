package com.amroz.placesapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import com.amroz.placesapp.Database.PlacesDatabase
import com.amroz.placesapp.Database.migration
import com.amroz.placesapp.Places

import java.util.concurrent.Executors

private const val DATABASE_NAME = "Placess_database"
class PlacesRepository private constructor(context: Context){
    private  val database: PlacesDatabase = Room.databaseBuilder(
        context.applicationContext,PlacesDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(migration).build()

    private  val Dao=database.PlacesDao()
    private  var executor = Executors.newSingleThreadExecutor()



     // add new Placess
    fun addnewPlaces(plase: Places){
         executor.execute{
             Dao.addPlaces(plase)
         }
     }


    // select  all

    fun getPlacess(): LiveData<List<Places>> = Dao.getPlaces()
    fun getHospitals(): LiveData<List<Places>> = Dao.getPlacesInHospitals()
    fun getSchools(): LiveData<List<Places>> = Dao.getPlacesSchools()
    fun getRestaurants(): LiveData<List<Places>> = Dao.getPlacesRestaurants()
    fun getCafes(): LiveData<List<Places>> = Dao.getPlacesCafes()
    fun getSuperMarket(): LiveData<List<Places>> = Dao.getPlacesSuperMarket()


    fun deletePlaces(plase: Places){
        executor.execute{
            Dao.deletePlaces(plase)
        }
    }








    companion object {
        private var INSTANCE: PlacesRepository? = null
        fun initialize(context: Context)
        {
            if (INSTANCE == null)
            {      INSTANCE = PlacesRepository(context)
            }
        }
        fun get():
                PlacesRepository {
            return INSTANCE ?:
            throw IllegalStateException("PlaceRepository must be initialized")
        }
    }
}