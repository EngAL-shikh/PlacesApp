package com.amroz.placesapp.Database


import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.amroz.placesapp.Places


@Dao
interface  Dao{

 @Insert
 fun addPlaces(places: Places)

 //get Places in to DO
 @Query("SELECT * FROM Places")
 fun getPlaces():LiveData<List<Places>>

 //get the Places in  Inprogress
 @Query("SELECT * FROM Places where type=2 ORDER BY id DESC ")
 fun getPlacesInProgress():LiveData<List<Places>>

 //get the Places in  Done
 @Query("SELECT * FROM Places where type=3  ORDER BY id DESC")
 fun getPlacesDone():LiveData<List<Places>>

 @Update
 fun updatePlaces(places: Places)

 //update
 @Query("UPDATE Places SET type= :level where id= :id ")
 fun updatePlacesToInprogress(level: Int,id:Int )

 @Delete
 fun deletePlaces(places: Places)


}