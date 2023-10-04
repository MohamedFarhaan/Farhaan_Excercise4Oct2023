package com.example.excercise4oct2023.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BLOOD_GROUP")
data class BloodGroup (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "blood_group")
    var blood_group: String,
)