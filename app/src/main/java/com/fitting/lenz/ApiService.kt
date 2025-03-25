package com.fitting.lenz

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
import com.fitting.lenz.models.TrackingOtpReqBody
import com.fitting.lenz.models.TrackingOtpResponse
import com.fitting.lenz.models.UpdatedFittingChargesData
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

private val okHttpClient = OkHttpClient.Builder()
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

const val lenzApiKey = "a99ed202v335i6d3v763e447k4417f8b"

interface ApiService {

    @Headers("lenz-api-key: $lenzApiKey")
    @POST("admin/login")
    suspend fun getAdminLogin(
        @Body adminLoginBody: AdminLoginBody
    ): AdminLoginResponse

    @Headers("lenz-api-key: $lenzApiKey")
    @GET("admin")
    suspend fun getAdminDetails(): GetAdminResponse

    @Headers("lenz-api-key: $lenzApiKey")
    @GET("shops")
    suspend fun getShops(): List<ShopDetails>

    @Headers("lenz-api-key: $lenzApiKey")
    @GET("charges/shiftingCharges")
    suspend fun getShiftingCharges(): ShiftingChargesResponse

    @Headers("lenz-api-key: $lenzApiKey")
    @GET("charges/fittingCharges")
    suspend fun getFittingCharges(): FittingChagresResponse

    @Headers("lenz-api-key: $lenzApiKey")
    @PUT("charges/update-shifting-charges")
    suspend fun updateShiftingCharges(
        @Body shiftingCharges: ShiftingChargesUpdated
    ): PriceUpdateResponse

    @Headers("lenz-api-key: $lenzApiKey")
    @PUT("charges/update-fitting-charges")
    suspend fun updateFittingCharges(
        @Body fittingCharges: UpdatedFittingChargesData
    ): PriceUpdateResponse

    @Headers("lenz-api-key: $lenzApiKey")
    @GET("orders/get-all-group-orders")
    suspend fun getGroupOrders(): GroupOrderResponse

    @Headers("lenz-api-key: $lenzApiKey")
    @PATCH("orders/{groupOrderId}/complete-work")
    suspend fun workCompleted(
        @Path("groupOrderId") groupOrderId: String
    )

    @Headers("lenz-api-key: $lenzApiKey")
    @PUT("shops/{shopId}/edit-distance")
    suspend fun editShopDistance(
        @Path("shopId") shopId: Long,
        @Body newDistance: ShopDistance
    )

    @Headers("lenz-api-key: $lenzApiKey")
    @PUT("shops/{shopId}/edit-credit-bal")
    suspend fun editCreditBalance(
        @Path("shopId") shopId: Long,
        @Body newBalance: CreditAmount
    )

    @Headers("lenz-api-key: $lenzApiKey")
    @POST("orders/call-for-pickup")
    suspend fun callForPickup(
        @Body groupOrderIds: CallForPickupRequest
    ): Response<ResponseBody>

    @Headers("lenz-api-key: $lenzApiKey")
    @POST("otp/request-tracking-otp")
    suspend fun getTrackingOtp(
        @Body otpRequestBody: TrackingOtpReqBody
    ): TrackingOtpResponse

    @Headers("lenz-api-key: $lenzApiKey")
    @GET("riders")
    suspend fun getAllRiders(): List<RiderDetails>

    @Headers("lenz-api-key: $lenzApiKey")
    @GET("riders")
    suspend fun getActiveOrders(): List<RiderDetails>
}