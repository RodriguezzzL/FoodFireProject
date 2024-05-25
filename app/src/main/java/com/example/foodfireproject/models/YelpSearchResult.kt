package com.example.foodfireproject.models

data class YelpSearchResult(
    val businesses: List<YelpBusiness>
)

data class YelpBusiness(
    val name: String,
    val rating: Double,
    val price: String?,
    val location: YelpLocation,
    val image_url: String
)

data class YelpLocation(
    val address1: String,
    val city: String,
    val state: String,
    val country: String
)