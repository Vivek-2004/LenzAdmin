package com.fitting.lenz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitting.lenz.models.AdminLoginBody
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LenzViewModel: ViewModel() {
    private val _lenzService = lenzService

    var adminConfirmation by mutableStateOf(false)
        private set

    var orderId by mutableIntStateOf(123)
        private set
    var shopName by mutableStateOf("ABC Specs")
        private set
    var orderType by mutableStateOf("Regular")
        private set
    var orderTime by mutableStateOf("09:17 a.m.")
        private set
    var paymentStatus by mutableStateOf("COD")
        private set

    fun verifyAdmin(
        id: Int,
        pass: String
    ) {
        val adminVerification = AdminLoginBody(
            adminId = id,
            password = pass
        )
        viewModelScope.launch {
            try {
                val adminResponse = _lenzService.getAdminLogin(adminVerification)
                adminConfirmation = adminResponse.confirmation
            } catch (e: HttpException) {
                adminConfirmation = false
            }
        }
    }
}