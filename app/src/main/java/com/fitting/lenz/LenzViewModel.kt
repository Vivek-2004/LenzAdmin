package com.fitting.lenz

import androidx.compose.runtime.getValue
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