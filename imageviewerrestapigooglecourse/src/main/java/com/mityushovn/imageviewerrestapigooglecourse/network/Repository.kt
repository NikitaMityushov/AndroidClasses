package com.mityushovn.imageviewerrestapigooglecourse.network

import android.content.Context
import com.mityushovn.imageviewerrestapigooglecourse.models.MarsProperty
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.IllegalStateException

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

class Repository private constructor(context: Context) {

    private val marsApiService: MarsApiService by lazy {
        configureRetrofit()
    }

    private fun configureRetrofit(): MarsApiService {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(MarsApiService::class.java)
    }

    suspend fun getProperties(): List<MarsProperty> {
        return marsApiService.getProperties()
    }

    // singleton
    companion object {
        private var instance: Repository? = null

        fun init(context: Context) {
            if (instance == null) {
                instance = Repository(context)
            }
        }

        fun get(): Repository {
            return instance ?: throw IllegalStateException("StockRepository must by initialized")
        }
    }
}