package com.example.secureotp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.secureotp.ui.LoginScreen
import com.example.secureotp.ui.OtpScreen
import com.example.secureotp.ui.SessionScreen
import com.example.secureotp.viewmodel.AuthState
import com.example.secureotp.viewmodel.AuthViewModel

@Composable
fun PasswordlessAuthApp() {

    val viewModel: AuthViewModel = viewModel()

    when (val state = viewModel.authState) {

        is AuthState.Login -> {
            LoginScreen {
                viewModel.sendOtp(it)
            }
        }

        is AuthState.Otp -> {
            OtpScreen(
                email = state.email,
                remainingSeconds = viewModel.remainingSeconds,
                errorMessage = viewModel.otpErrorMessage,
                onVerify = {
                    viewModel.verifyOtp(state.email, it)
                },
                onResend = {
                    viewModel.sendOtp(state.email)
                }
            )
        }


        is AuthState.Session -> {
            SessionScreen(state.startTime) {
                viewModel.logout()
            }
        }
    }
}
