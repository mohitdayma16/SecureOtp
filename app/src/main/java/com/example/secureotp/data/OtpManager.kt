package com.example.secureotp.data

import com.example.secureotp.viewmodel.OtpResult
import java.util.*

data class OtpData(
    val otp: String,
    val timestamp: Long,
    var attempts: Int
)

class OtpManager {

    private val otpStore = mutableMapOf<String, OtpData>()

    fun generateOtp(email: String): String {
        val otp = (100000..999999).random().toString()
        otpStore[email] = OtpData(
            otp = otp,
            timestamp = System.currentTimeMillis(),
            attempts = 0
        )
        return otp
    }

    fun validateOtp(email: String, enteredOtp: String): OtpResult {
        val data = otpStore[email] ?: return OtpResult.EXPIRED

        val isExpired = System.currentTimeMillis() - data.timestamp > 60_000
        if (isExpired) return OtpResult.EXPIRED

        if (data.attempts >= 3) return OtpResult.ATTEMPTS_EXCEEDED

        data.attempts++

        return if (data.otp == enteredOtp) {
            OtpResult.SUCCESS
        } else {
            OtpResult.INVALID
        }
    }

}
