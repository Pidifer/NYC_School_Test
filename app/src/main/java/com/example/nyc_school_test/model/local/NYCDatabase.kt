package com.example.nyc_school_test.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NycSchoolEntity::class],
    version = 1
)
abstract class NYCDatabase: RoomDatabase() {
    abstract fun nycDao(): NYCDao

    companion object{
        private var INSTANCE: NYCDatabase? = null

        fun newInstance(context: Context): NYCDatabase =
            INSTANCE ?: synchronized(this){
                var temp = INSTANCE
                if(temp != null) return temp

                temp = Room.databaseBuilder(
                    context,
                    NYCDatabase::class.java,
                    "room_nyc_db"
                ).build()
                INSTANCE = temp
                temp
            }
    }
}