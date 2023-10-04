package com.example.excercise4oct2023.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.excercise4oct2023.db.entity.BloodGroup
import com.example.excercise4oct2023.db.entity.People

@Dao
interface BloodGroupDao {
    @Query("SELECT * FROM BLOOD_GROUP")
    suspend fun getAllBloodGroupData(): List<BloodGroup>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBloodGroup(vararg items: BloodGroup)
}