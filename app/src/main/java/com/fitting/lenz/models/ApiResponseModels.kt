package com.fitting.lenz.models

data class TestResponse(
    val _id: String,
    val notification: Boolean
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

data class AdminDetails(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val adminId: Int,
    val createdAt: String,
    val updatedAttr: String,
    val __v : Int
)