package com.fitting.lenz

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private val retrofit = Retrofit
    .Builder()
    .baseUrl("https://lenz-backend.onrender.com/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val lenzService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {

    @Headers("lenz-api-key: a99ed2023194a3356d37634474417f8b")
    @POST("admin/login")
    suspend fun getAdminLogin(
        @Body adminLoginBody: AdminLoginBody
    ): AdminLoginResponse

    @GET("test")
    suspend fun getTest(): List<TestResponse>
}