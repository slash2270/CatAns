package com.example.catans.network

import com.example.catans.model.Airport
import com.example.catans.model.Currency
import com.example.catans.util.EnumUtils
import com.example.catans.util.Utils.Companion.URI_AIRPORT_DEPARTURE
import com.example.catans.util.Utils.Companion.URI_AIRPORT_INBOUND
import com.example.catans.util.Utils.Companion.URI_CURRENCY
import com.example.catans.util.Utils.Companion.URL_AIRPORT
import com.example.catans.util.Utils.Companion.URL_CURRENCY
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET(URI_CURRENCY)
    fun getCurrency(): Call<Currency?>

    @GET(URI_AIRPORT_DEPARTURE)
    suspend fun getAirportDeparture(@Query("page") page: Int, @Query("per_page") perPage: Int): List<Airport>?

    @GET(URI_AIRPORT_INBOUND)
    suspend fun getAirportInbound(@Query("page") page: Int, @Query("per_page") perPage: Int): List<Airport>?

    companion object {
        private fun getUrl(enumUtils: EnumUtils): String {
            return when(enumUtils) {
                EnumUtils.Inbound, EnumUtils.Departure-> URL_AIRPORT
                EnumUtils.Currency -> URL_CURRENCY
            }
        }

        fun create(enumUtils: EnumUtils): ApiService {
            val okHttpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout( 10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
//                .addInterceptor()
//                .authenticator()
//                .proxy()
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getUrl(enumUtils))
                .build()
                .create(ApiService::class.java)
        }
    }
}