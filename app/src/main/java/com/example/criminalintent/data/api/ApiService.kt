package com.example.criminalintent.data.api

import com.example.criminalintent.data.Model.Photos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // api_key - d094828373b3b5da44175d39cf879cbf
    // topPhoto - https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=d094828373b3b5da44175d39cf879cbf&format=json&nojsoncallback=1&extras=url_s
    // search - https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=d094828373b3b5da44175d39cf879cbf&format=json&nojsoncallback=1&extras=url_s&text=

    @GET("services/rest/?method=flickr.interestingness.getList&api_key=d094828373b3b5da44175d39cf879cbf&format=json&nojsoncallback=1&extras=url_s")
    suspend fun getTopPhotos(): Response<Photos>

    @GET("services/rest/?method=flickr.photos.search&api_key=d094828373b3b5da44175d39cf879cbf&format=json&nojsoncallback=1&extras=url_s")
    suspend fun getTagsPhotos(@Query("text") text: String): Response<Photos>
}