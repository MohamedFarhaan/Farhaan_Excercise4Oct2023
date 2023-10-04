package com.example.excercise4oct2023.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "People",
    foreignKeys = [
        ForeignKey(
            entity = BloodGroup::class,
            parentColumns = ["id"],
            childColumns = ["blood_group_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class People (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "blood_group_id")
    var blood_group_id: Int,

    var blood_group: String,
)