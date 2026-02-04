package com.example.secureotp.viewmodel


sealed class AuthState {
    object Login : AuthState()
    data class Otp(val email: String) : AuthState()
    data class Session(val startTime: Long) : AuthState()
}
