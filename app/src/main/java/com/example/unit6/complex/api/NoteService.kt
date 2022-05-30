package com.example.unit6.complex.api

import com.example.unit6.complex.api.model.NoteApiModel
import com.example.unit6.complex.api.model.NoteIdApiModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "https://unit6-575b0-default-rtdb.firebaseio.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface NoteService {
    @GET("note.json")
    suspend fun getNotes(): Map<String, NoteApiModel>

    @POST("note.json")
    suspend fun submitNote(@Body data: NoteApiModel): NoteIdApiModel
}

object NoteApi {
    val retrofitService: NoteService by lazy { retrofit.create(NoteService::class.java) }
}
