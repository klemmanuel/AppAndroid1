package com.ismin.projectapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CardService {

    @GET("cards")
    fun getAllCards(): Call<ArrayList<Card>>

    @POST("cards")
    fun createCard(@Body() card: Card): Call<Card>
}