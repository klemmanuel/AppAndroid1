package com.ismin.projectapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CardService {

    @GET("/cards")
    fun getAllCards(): Call<MutableList<Card>>

    @GET("/cards/{id}")
    fun getCard(@Path("id") id: String): Call<Card>

    @POST("/cards")
    fun createCard(@Body() card: Card): Call<Card>
}