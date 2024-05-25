package com.example.foodfireproject
import com.example.foodfireproject.api.YelpApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YelpService {
    private  val BASE_URL = "https://api.yelp.com/v3/"
    private  val API_KEY = "_tP89poAYUpc_7TO8zMtjyUfgUlDAASLx8A4PI9Lktj-2amEqaK_EqswoeGlJB2elz0JVqebW7SCy0mHbEYp7U3fLZvdzKZizSC62fIUdt4KOFWB5I39MjR2afdQZnYx"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: YelpApi = retrofit.create(YelpApi::class.java)

    fun getAuthHeader(): String = "Bearer $API_KEY"
}