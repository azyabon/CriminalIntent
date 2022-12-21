package com.example.criminalintent.data.Model

import com.example.criminalintent.IntentModel

data class PhotosX(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<IntentModel>,
    val total: Int
)