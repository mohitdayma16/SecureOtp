package com.example.secureotp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.secureotp.data.OtpManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job


class AuthViewModel : ViewModel() {

    private val otpManager = OtpManager()
    private var otpCountdownJob: Job? = null


    var authState by mutableStateOf<AuthState>(AuthState.Login)
        private set
    var remainingSeconds by mutableStateOf(60)
        private set
    var otpErrorMessage by mutableStateOf<String?>(null)
        private set


    fun sendOtp(email: String) {
        val otp = otpManager.generateOtp(email)

        Log.d("OTP_DEBUG", "OTP for $email is $otp")

        otpCountdownJob?.cancel()   // cancel old countdown
        remainingSeconds = 60

        otpCountdownJob = viewModelScope.launch {
            while (remainingSeconds > 0) {
                delay(1000)
                remainingSeconds--
            }
        }

        authState = AuthState.Otp(email)
    }

    fun verifyOtp(email: String, otp: String) {
        when (otpManager.validateOtp(email, otp)) {

            OtpResult.SUCCESS -> {
                otpErrorMessage = null
                authState = AuthState.Session(System.currentTimeMillis())
            }

            OtpResult.INVALID -> {
                otpErrorMessage = "Incorrect OTP. Please try again."
            }

            OtpResult.EXPIRED -> {
                otpErrorMessage = "OTP has expired. Please request a new one."
            }

            OtpResult.ATTEMPTS_EXCEEDED -> {
                otpErrorMessage = "Maximum attempts exceeded. Please resend OTP."
            }
        }
    }

    fun logout() {
        authState = AuthState.Login
    }
}