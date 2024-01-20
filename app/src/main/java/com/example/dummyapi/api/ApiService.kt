package com.example.dummyapi.api

import com.example.dummyapi.data.ProductAgain
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    fun getAll(): Call<ProductAgain>
}