package com.example.excercise4oct2023.db

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.excercise4oct2023.db.dao.BloodGroupDao
import com.example.excercise4oct2023.db.dao.PeopleDao
import com.example.excercise4oct2023.db.entity.BloodGroup
import com.example.excercise4oct2023.db.entity.People
import kotlinx.coroutines.launch

@Database(entities = [BloodGroup::class, People::class], version = 1)
abstract class DatabaseInit : RoomDatabase() {
    abstract fun bloodGroupDao(): BloodGroupDao
    abstract fun peopleDao(): PeopleDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseInit? = null

        @Composable
        fun getDatabase(context: Context): DatabaseInit {
            return INSTANCE ?: synchronized(this) {
                val bloodGroup1 = BloodGroup(id = 1, blood_group = "A+")
                val bloodGroup2 = BloodGroup(id = 2, blood_group = "A-")
                val bloodGroup3 = BloodGroup(id = 3, blood_group = "B+")
                val bloodGroup4 = BloodGroup(id = 4, blood_group = "B-")
                val bloodGroup5 = BloodGroup(id = 6, blood_group = "AB+")
                val bloodGroup6 = BloodGroup(id = 7, blood_group = "AB-")
                val bloodGroup7 = BloodGroup(id = 8, blood_group = "O+")
                val bloodGroup8 = BloodGroup(id = 9, blood_group = "O-")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseInit::class.java,
                    "oct_4_2023_task.db"
                ).build()

                var scope = rememberCoroutineScope()
                scope.launch {
                    instance.bloodGroupDao().insertBloodGroup(bloodGroup1, bloodGroup2, bloodGroup3,
                        bloodGroup4, bloodGroup5, bloodGroup6, bloodGroup7, bloodGroup8)
                }
                INSTANCE = instance
                instance
            }
        }
    }
}