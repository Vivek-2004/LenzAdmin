package com.fitting.lenz

import com.fitting.lenz.models.ActiveOrders
import com.fitting.lenz.models.AdminLoginBody
import com.fitting.lenz.models.AdminLoginResponse
import com.fitting.lenz.models.CallForPickupRequest
import com.fitting.lenz.models.CreditAmount
import com.fitting.lenz.models.FittingChagresResponse
import com.fitting.lenz.models.GetAdminResponse
import com.fitting.lenz.models.GroupOrderResponse
import com.fitting.lenz.models.PriceUpdateResponse
import com.fitting.lenz.models.RiderDetails
import com.fitting.lenz.models.ShiftingChargesResponse
import com.fitting.lenz.models.ShiftingChargesUpdated
import com.fitting.lenz.models.ShopDetails
import com.fitting.lenz.models.ShopDistance
import com.fitting.lenz.models.UpdatedFittingChargesData
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

const val LENZ_API_KEY = "a99ed202v335i6d3v763e447k4417f8b"

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("lenz-api-key", LENZ_API_KEY)
            .build()
        chain.proceed(request)
    }
    .connectTimeout(45, TimeUnit.SECONDS)
    .readTimeout(45, TimeUnit.SECONDS)
    .writeTimeout(45, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("http://13.201.223.127/api/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val lenzService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {

    @POST("admin/login")
    suspend fun getAdminLogin(
        @Body adminLoginBody: AdminLoginBody
    ): AdminLoginResponse

    @GET("admin")
    suspend fun getAdminDetails(): GetAdminResponse

    @GET("shops")
    suspend fun getShops(): List<ShopDetails>

    @GET("charges/shiftingCharges")
    suspend fun getShiftingCharges(): ShiftingChargesResponse

    @GET("charges/fittingCharges")
    suspend fun getFittingCharges(): FittingChagresResponse

    @PUT("charges/update-shifting-charges")
    suspend fun updateShiftingCharges(
        @Body shiftingCharges: ShiftingChargesUpdated
    ): PriceUpdateResponse

    @PUT("charges/update-fitting-charges")
    suspend fun updateFittingCharges(
        @Body fittingCharges: UpdatedFittingChargesData
    ): PriceUpdateResponse

    @GET("orders/get-all-group-orders")
    suspend fun getGroupOrders(): GroupOrderResponse

    @PATCH("orders/{groupOrderId}/complete-work")
    suspend fun workCompleted(
        @Path("groupOrderId") groupOrderId: String
    )

    @PUT("shops/{shopId}/edit-distance")
    suspend fun editShopDistance(
        @Path("shopId") shopId: Int,
        @Body newDistance: ShopDistance
    )

    @PUT("shops/{shopId}/edit-credit-bal")
    suspend fun editCreditBalance(
        @Path("shopId") shopId: Int,
        @Body newBalance: CreditAmount
    )

    @POST("orders/call-for-pickup")
    suspend fun callForPickup(
        @Body groupOrderIds: CallForPickupRequest
    ): Response<ResponseBody>

    @GET("riders")
    suspend fun getAllRiders(): List<RiderDetails>

    @GET("orders/active-admin-orders/{adminObjectId}")
    suspend fun getActiveOrders(
        @Path("adminObjectId") adminObjectId: String
    ): ActiveOrders
}