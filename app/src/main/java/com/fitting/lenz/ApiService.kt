package com.fitting.lenz

import com.fitting.lenz.models.AdminLoginBody
import com.fitting.lenz.models.AdminLoginResponse
import com.fitting.lenz.models.FittingChagresResponse
import com.fitting.lenz.models.PriceUpdateResponse
import com.fitting.lenz.models.ShiftingChargesResponse
import com.fitting.lenz.models.ShiftingChargesUpdated
import com.fitting.lenz.models.ShopDetails
import com.fitting.lenz.models.TestResponse
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(50, TimeUnit.SECONDS)
    .readTimeout(50, TimeUnit.SECONDS)
    .writeTimeout(50, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://lenz-backend.onrender.com/api/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val lenzService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {

    @Headers("lenz-api-key: a99ed2023194a3356d37634474417f8b")
    @POST("admin/login")
    suspend fun getAdminLogin(
        @Body adminLoginBody: AdminLoginBody
    ): AdminLoginResponse

    @Headers("lenz-api-key: a99ed2023194a3356d37634474417f8b")
    @GET("shops")
    suspend fun getShops(): List<ShopDetails>

    @GET("charges/shiftingCharges")
    suspend fun getShiftingCharges(): ShiftingChargesResponse

    @GET("charges/fittingCharges")
    suspend fun getFittingCharges(): FittingChagresResponse

    @Headers("lenz-api-key: a99ed2023194a3356d37634474417f8b")
    @PUT("charges/update-shifting-charges")
    suspend fun updateShiftingCharges(
        @Body shiftingCharges: ShiftingChargesUpdated
    ): PriceUpdateResponse

    @GET("test")
    suspend fun getTest(): List<TestResponse>
}