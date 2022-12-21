package com.example.criminalintent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_table")
data class IntentModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo
    val farm: Int?,
    @ColumnInfo
    val height_s: Int?,
    @ColumnInfo
    val isfamily: Int?,
    @ColumnInfo
    val isfriend: Int?,
    @ColumnInfo
    val ispublic: Int?,
    @ColumnInfo
    val owner: String?,
    @ColumnInfo
    val secret: String?,
    @ColumnInfo
    val server: String?,
    @ColumnInfo
    val title: String?,
    @ColumnInfo
    val url_s: String,
    @ColumnInfo
    val width_s: Int?
)
