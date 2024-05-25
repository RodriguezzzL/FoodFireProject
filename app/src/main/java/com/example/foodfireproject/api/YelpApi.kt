package com.example.foodfireproject.api
import com.example.foodfireproject.models.YelpSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpApi {


        @GET("businesses/search")
        fun searchRestaurants(
            @Header("Authorization") authHeader: String,
            @Query("term") term: String,
            @Query("location") location: String
        ): Call<YelpSearchResult>
    }

