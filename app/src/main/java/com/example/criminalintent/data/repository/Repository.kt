package com.example.criminalintent.data.repository

import com.example.criminalintent.data.Model.Photos
import com.example.criminalintent.data.api.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun getPhotos(): Response<Photos>{
        return RetrofitInstance.api.getTopPhotos()
    }

    suspend fun getTagsPhotos(tag: String): Response<Photos>{
        return RetrofitInstance.api.getTagsPhotos(tag)
    }
}