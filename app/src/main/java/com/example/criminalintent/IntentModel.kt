package com.example.criminalintent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_table")
data class IntentModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val imageId: ByteArray,
)
