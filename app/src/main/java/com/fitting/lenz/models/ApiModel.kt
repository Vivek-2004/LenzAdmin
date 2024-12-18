package com.fitting.lenz.models

import com.google.gson.annotations.SerializedName

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

data class AdminDetails(
    val _id: String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val adminId: Int,
    val createdAt: String,
    val updatedAttr: String,
    val __v : Int
)

data class ShopDetails(
    val _id: String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val plan: String,
    val userId: Long,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
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

data class PriceUpdateResponse(
    val message: String,
    val confirmation: Boolean
)

data class TestResponse(
    val _id: String,
    val notification: Boolean
)