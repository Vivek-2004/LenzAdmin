package com.fitting.lenz.models

import com.google.gson.annotations.SerializedName

data class AdminDetails(
    val address: Address,
    val _id: String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val adminId: Int,
    val authToken: String,
    val createdAt: String,
    val updatedAt: String
)

data class RiderDetails(
    val _id: String,
    val riderId: String,
    val name: String,
    val phone: String,
    val email: String,
    val vehicleNumber: String,
    var isAvailable: Boolean,
    val isWorking: Boolean,
    val totalOrders: Int,
    val dailyOrders: Int,
    val totalEarnings: Double,
    val dailyEarnings: Double,
    val createdAt: String,
    val updatedAt: String
)

data class AdminLoginBody(
    val adminId: Int,
    val password: String
)

data class AdminLoginResponse(
    val message: String,
    val confirmation: Boolean,
    val token: String,
    val admin: AdminDetails
)

data class GetAdminResponse(
    val admin: List<AdminDetails>,
    val confirmation: Boolean
)

data class ShopDetails(
    val _id: String,
    val name: String,
    val email: String,
    val phone: String,
    val alternatePhone: String,
    val password: String,
    val shopName: String,
    val plan: String,
    val userId: Long,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val address: Address,
    val distance: Int,
    val creditBalance: Double
)

data class Address(
    val line1: String,
    val line2: String,
    val landmark: String,
    val city: String,
    val state: String,
    val pinCode: String
)

data class ShiftingChargesResponse(
    val _id: String,
    val type: String,
    val data: ShiftingCharges,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

data class ShiftingCharges(
    @SerializedName("Full Frame") val fullFrame: Int,
    @SerializedName("Supra") val supra: Int,
    @SerializedName("Rimless") val rimless: Int
)

data class ShiftingChargesUpdated(
    val FullFrame: Int,
    val Supra: Int,
    val Rimless: Int
)

data class SingleDouble(
    @SerializedName("Single") val single: Int,
    @SerializedName("Double") val double: Int
)

data class LowHigh(
    val low: SingleDouble,
    val high: SingleDouble
)

data class FittingDataFullFrame(
    @SerializedName("Normal") val normal: LowHigh,
    @SerializedName("PR") val pr: LowHigh,
    @SerializedName("Sunglass") val sunglass: LowHigh
)

data class FittingDataSupra(
    @SerializedName("Normal") val normal: LowHigh,
    @SerializedName("PR") val pr: LowHigh,
    @SerializedName("Sunglass") val sunglass: LowHigh
)

data class FittingDataRimless(
    @SerializedName("Normal") val normal: LowHigh,
    @SerializedName("PR") val pr: LowHigh,
    @SerializedName("Poly") val poly: LowHigh,
    @SerializedName("PolyPR") val polyPR: LowHigh,
    @SerializedName("Sunglass") val sunglass: LowHigh
)

data class FittingChargesData(
    @SerializedName("Full Frame") val fullFrame: FittingDataFullFrame,
    @SerializedName("Supra") val supra: FittingDataSupra,
    @SerializedName("Rimless") val rimless: FittingDataRimless
)

data class UpdatedFittingChargesData(
    @SerializedName("data") val data: FittingChargesData
)

data class FittingChagresResponse(
    val _id: String,
    val type: String,
    val data: FittingChargesData,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

data class PriceUpdateResponse(
    val message: String,
    val confirmation: Boolean
)

data class GroupOrderResponse(
    val data: List<GroupOrder>
)

data class PickupObject(
    val key: String
)

data class GroupOrder(
    val paidAmount: Int,
    val leftAmount: Int,
    @SerializedName("tracking_status") var trackingStatus: String,
    @SerializedName("rider_id") val riderId: String?,
    @SerializedName("admin_id") val adminId: String?,
    @SerializedName("_id") val id: String,
    @SerializedName("shop_pickup") val shopPickup: PickupObject,
    @SerializedName("admin_pickup") val adminPickup: PickupObject,
    val userId: String,
    val orders: List<Order>,
    val totalAmount: Int,
    val deliveryCharge: Int,
    val finalAmount: Int,
    val paymentStatus: String,
    val createdAt: String,
)

data class Order(
    val deliveryCharge: Int,
    @SerializedName("_id") val id: String,
    val userId: String,
    val customerDetails: CustomerDetails,
    val frameOptions: FrameOptions,
    @SerializedName("shiftingOrFitting") val shiftingOrFitting: String,
    val purchaseLens: String?,
    val glassType: String?,
    val lensDetails: String?,
    val materialDetails: String?,
    val coatingDetails: String?,
    val powerDetails: PowerDetails?,
    val powerType: String?,
    val powerEntryType: String?,
    val fittingCharge: Int,
    val shiftingCharge: Int,
    val totalAmount: Int,
    val paymentType: String?,
    val orderPlaced: Boolean,
    @SerializedName("isGroupOrder") val isGroupOrder: Boolean,
    val paymentStatus: String,
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("__v") val version: Int,
    @SerializedName("groupOrderId") val groupOrderId: String?
)

data class CustomerDetails(
    val name: String,
    val billNumber: String?
)

data class FrameOptions(
    val type: String
)

data class PowerDetails(
    val right: PowerDetail,
    val left: PowerDetail
)

data class PowerDetail(
    val spherical: String?,
    val cylindrical: String?,
    val axis: String?,
    val addition: String?
)

data class ShopDistance(
    val newDistance: Int
)

data class CreditAmount(
    val newCreditAmt: Double
)

data class CallForPickupRequest(
    val groupOrderIds: List<String>,
    val delAmount: Double
)

data class TrackingOtpReqBody(
    @SerializedName("groupOrder_id") val groupOrderId: String?,
    @SerializedName("order_key") val orderKey: String?,
    val purpose: String
)

data class TrackingOtpResponse(
    @SerializedName("groupOrder_id") val groupOrderId: String?,
    @SerializedName("order_key") val orderKey: String?,
    @SerializedName("otp_code") val otpCode: String,
    val purpose: String
)

data class ActiveOrdersData(
    val id: String,
    val orderKey: String,
    val trackingStatus: String,
    val otpCode: String,
    val deliveryPersonName: String,
    val deliveryPersonPhone: String,
    val groupOrderIds: List<String>
)

data class ActiveOrders(
    val data: List<ActiveOrdersData>
)