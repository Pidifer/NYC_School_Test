package com.example.nyc_school_test.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "school_list")
data class NycSchoolEntity(
    @PrimaryKey
    val dbn: String,
    @ColumnInfo(name = "name")
    val school_name: String,
    val location: String,
    val latitude: String,
    val longitude: String
)
