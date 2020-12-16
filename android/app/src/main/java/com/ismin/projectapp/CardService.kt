package com.ismin.projectapp

import retrofit2.Call
import retrofit2.http.*

interface CardService {
    @GET("cards")
    fun getAllCards(): Call<List<Card>>

    @GET("cards/{name}")
    fun getCard(@Path("name") name: String): Call<Card>

    @PUT("cards/{name}")
    fun updateCard(@Path("name") name: String, @Body() card: Card): Call<Card>
}