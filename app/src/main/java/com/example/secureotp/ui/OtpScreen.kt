package com.example.secureotp.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun OtpScreen(
    email: String,
    remainingSeconds: Int,
    errorMessage: String?,
    onVerify: (String) -> Unit,
    onResend: () -> Unit
) {
    var otp by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                // Header
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Verify your code 🔐",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = "We sent a 6-digit code to",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                    )
                }

                // OTP Input (centered & spaced)
                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    placeholder = { Text("• • • • • •") },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        letterSpacing = 8.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Error message
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Timer
                Text(
                    text = if (remainingSeconds > 0)
                        "Code expires in $remainingSeconds seconds"
                    else
                        "The code has expired",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (remainingSeconds > 0)
                        MaterialTheme.colorScheme.onSurfaceVariant
                    else
                        MaterialTheme.colorScheme.error
                )

                // Verify button
                Button(
                    onClick = { onVerify(otp) },
                    enabled = remainingSeconds > 0 && otp.length == 6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Confirm & Continue")
                }

                // Resend action
                TextButton(
                    onClick = onResend,
                    enabled = remainingSeconds == 0,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Didn’t receive a code? Resend")
                }
            }
        }
    }
}
