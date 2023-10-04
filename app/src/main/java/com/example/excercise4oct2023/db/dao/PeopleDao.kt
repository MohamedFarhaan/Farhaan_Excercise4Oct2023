package com.example.excercise4oct2023.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.excercise4oct2023.db.entity.People

@Dao
interface PeopleDao {
    @Query("SELECT PL.ID, PL.NAME, PL.BLOOD_GROUP_ID, BG.BLOOD_GROUP FROM PEOPLE PL JOIN BLOOD_GROUP BG ON PL.BLOOD_GROUP_ID = BG.ID")
    suspend fun getAllPeopleData(): List<People>

    @Query("SELECT PL.ID, PL.NAME, PL.BLOOD_GROUP_ID, BG.BLOOD_GROUP FROM PEOPLE PL JOIN BLOOD_GROUP BG ON PL.BLOOD_GROUP_ID = BG.ID WHERE BG.ID = :bloodGroupId")
    suspend fun getAllPeopleWithBloodGroup(bloodGroupId: Int): List<People>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPeople(vararg items: People)

    @Query("DELETE FROM PEOPLE WHERE NAME = :name")
    suspend fun deletePeople(name: String)
}