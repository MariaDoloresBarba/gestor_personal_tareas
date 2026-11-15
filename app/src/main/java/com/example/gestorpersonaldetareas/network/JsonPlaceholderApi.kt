package com.example.gestorpersonaldetareas.network

import retrofit2.http.GET
import retrofit2.http.Query

interface JsonPlaceholderApi {

    @GET("todos")
    suspend fun obtenerTareas(@Query("userId") userId: Int): List<TareaRemota>
}