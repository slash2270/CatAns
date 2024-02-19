package com.example.catans.network

import com.example.catans.model.Everything
import com.example.rooans.util.Utils.Companion.NEWS_URI
import com.example.rooans.util.Utils.Companion.NEWS_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET(NEWS_URI)
    suspend fun getData(@Query("page") page: Int, @Query("per_page") perPage: Int): Everything

    companion object {
        fun create(): NewsService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NEWS_URL)
                .build()
                .create(NewsService::class.java)
        }
    }
}