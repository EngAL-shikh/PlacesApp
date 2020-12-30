package com.amroz.placesapp

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity
data class Places (
    @PrimaryKey(autoGenerate = true) val id:Int,
    val title:String,
    val Describe:String,
    val latitude:Float,
    val longitude:Float,
    val type:String
): Serializable {


}



