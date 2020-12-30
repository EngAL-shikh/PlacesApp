package com.amroz.placesapp.Database


import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.amroz.placesapp.Places


@Dao
interface  Dao{

 @Insert
 fun addPlaces(places: Places)


 @Query("SELECT * FROM Places")
 fun getPlaces():LiveData<List<Places>>

 //get the Places
 @Query("SELECT * FROM Places where type='Hospitals' ORDER BY id DESC ")
 fun getPlacesInHospitals():LiveData<List<Places>>

 //get the Schools Places
 @Query("SELECT * FROM Places where type='Schools' ORDER BY id DESC ")
 fun getPlacesSchools():LiveData<List<Places>>

 //get the Restaurants Places
 @Query("SELECT * FROM Places where type='Restaurants' ORDER BY id DESC ")
 fun getPlacesRestaurants():LiveData<List<Places>>

 //get the Cafes Places
 @Query("SELECT * FROM Places where type='Cafes' ORDER BY id DESC ")
 fun getPlacesCafes():LiveData<List<Places>>


 //get the Super Market Places
 @Query("SELECT * FROM Places where type='Super Market' ORDER BY id DESC ")
 fun getPlacesSuperMarket():LiveData<List<Places>>



 @Update
 fun updatePlaces(places: Places)

 //update
 @Query("UPDATE Places SET type= :level where id= :id ")
 fun updatePlacesToInprogress(level: Int,id:Int )

 @Delete
 fun deletePlaces(places: Places)


}