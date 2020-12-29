package com.amroz.placesapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Places (
    @PrimaryKey(autoGenerate = true) val id:Int,
    val title:String,
    val Describe:String,
    val latitude:Float,
    val longitude:Float,
    val type:Int
) {
}