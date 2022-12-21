package com.example.criminalintent.data.Model

data class PhotosX(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)