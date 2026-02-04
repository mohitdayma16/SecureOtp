package com.example.secureotp.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsLogger(context: Context) {

    private val analytics: FirebaseAnalytics =
        FirebaseAnalytics.getInstance(context)

    fun logOtpGenerated(email: String) {
        val bundle = Bundle().apply {
            putString("email", email)
        }
        analytics.logEvent("otp_generated", bundle)
    }

    fun logOtpSuccess(email: String) {
        val bundle = Bundle().apply {
            putString("email", email)
        }
        analytics.logEvent("otp_success", bundle)
    }

    fun logOtpFailure(email: String) {
        val bundle = Bundle().apply {
            putString("email", email)
        }
        analytics.logEvent("otp_failure", bundle)
    }

    fun logLogout() {
        analytics.logEvent("logout", null)
    }
}
