package com.fitting.lenz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitting.lenz.models.AdminLoginBody
import com.fitting.lenz.models.ShiftingChargesUpdated
import com.fitting.lenz.models.ShopDetails
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LenzViewModel: ViewModel() {
    private val _lenzService = lenzService

    var adminConfirmation by mutableStateOf(false)
        private set
    var shopsList by mutableStateOf<List<ShopDetails>>(emptyList())
        private set

    var shiftingFullFrameCharges by mutableStateOf("0")
    var shiftingSupraCharges by mutableStateOf("0")
    var shiftingRimLessCharges by mutableStateOf("0")
    var shiftingUpdateConfirmation by mutableStateOf(false)
        private set

    var orderId by mutableIntStateOf(123)
        private set
    var orderedShopName by mutableStateOf("ABC Specs")
        private set
    var orderType by mutableStateOf("Regular")
        private set
    var orderTime by mutableStateOf("09:17 a.m.")
        private set
    var paymentStatus by mutableStateOf("COD")
        private set

    var test by mutableStateOf("TEST")

    init {
        getShopsList()
        getShiftingCharges()
        getFittingCharges()
    }

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
            } catch (e: Exception) {
                adminConfirmation = false
            }
        }
    }

    fun getShopsList() {
        viewModelScope.launch {
            try {
                val shopsResponse = _lenzService.getShops()
                shopsList = shopsResponse
            } catch (e: Exception) {
                shopsList = emptyList()
            }
        }
    }

    fun getShiftingCharges() {
        viewModelScope.launch {
            try {
                val shiftingChargesResponse = _lenzService.getShiftingCharges()
                shiftingFullFrameCharges = shiftingChargesResponse.data.fullFrame.toString()
                shiftingSupraCharges = shiftingChargesResponse.data.supra.toString()
                shiftingRimLessCharges = shiftingChargesResponse.data.rimless.toString()
            } catch (e: HttpException) {
                shiftingFullFrameCharges = "-1"
                shiftingSupraCharges = "-1"
                shiftingRimLessCharges = "-1"
            }
        }
    }

    fun getFittingCharges() {
        viewModelScope.launch {
            try {
                val fittingChargesResponse = _lenzService.getFittingCharges()
                test = fittingChargesResponse.toString()
            } catch (e:Exception) {
                test = "An Exception Occurred : $e"
            }
        }
    }

    fun updateShiftingCharges(
        fullFrame: Int,
        supra: Int,
        rimless: Int
    ){
        val updatedCharges = ShiftingChargesUpdated(
            FullFrame = fullFrame,
            Supra = supra,
            Rimless = rimless
        )
        viewModelScope.launch {
            try {
                val shiftingUpdateResponse = _lenzService.updateShiftingCharges(updatedCharges)
                shiftingUpdateConfirmation = shiftingUpdateResponse.confirmation
            } catch (e: Exception) {
                shiftingUpdateConfirmation = false
            }
        }
    }
}