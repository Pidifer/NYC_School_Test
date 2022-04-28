package com.example.nyc_school_test.model.local

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NYCDao {
    @Update(
        entity = NycSchoolEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun update(nycSchoolEntity: NycSchoolEntity)

    @Query(value = "SELECT * FROM school_list")
    suspend fun getListSchools(): List<NycSchoolEntity>

}