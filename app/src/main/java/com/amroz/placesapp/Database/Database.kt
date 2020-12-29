package com.amroz.placesapp.Database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amroz.placesapp.Places


@Database(entities = [Places::class],version = 2, exportSchema = false)

abstract class PlacesDatabase:RoomDatabase() {

    abstract  fun PlacesDao(): Dao
}

val migration=object : Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Places ADD COLUMN title TEXT NOT NULL DEFAULT ''")
    }
}