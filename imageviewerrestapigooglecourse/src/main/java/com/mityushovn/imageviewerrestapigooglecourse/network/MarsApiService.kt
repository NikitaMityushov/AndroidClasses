package com.mityushovn.imageviewerrestapigooglecourse.network

import com.mityushovn.imageviewerrestapigooglecourse.models.MarsProperty
import retrofit2.http.GET

interface MarsApiService {

    @GET("realestate")
    suspend fun getProperties(): List<MarsProperty>

}