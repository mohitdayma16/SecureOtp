# SecureOtp – Passwordless Email + OTP Authentication

## Overview
SecureOtp is an Android application that implements a **passwordless login system using Email and OTP**, followed by a session screen that shows how long the user stays logged in.

All OTP-related logic is handled **entirely on the device**, with no backend involved, as required for this assignment.

The app is developed using **Kotlin and Jetpack Compose**, follows a **ViewModel-driven architecture**, and uses **Firebase Analytics** only for tracking app events.

---

## Technology Used
- **Programming Language:** Kotlin  
- **UI Framework:** Jetpack Compose (Material 3)  
- **Architecture Pattern:** ViewModel with UI State  
- **Async Handling:** Kotlin Coroutines  
- **Analytics:** Firebase Analytics  
- **Development Tool:** Android Studio  

---

## Login Flow (Email + OTP)

1. The user enters an email address.
2. On tapping **Send OTP**, a **6-digit OTP** is generated locally.
3. The user enters the OTP to verify and complete login.

⚠️ Since no backend is used, the OTP is **not sent via email**.  
For testing and demonstration, the OTP is printed in **Logcat**.

---

## OTP Behavior & Rules

| Feature | Details |
|------|------|
| OTP format | 6-digit numeric |
| Validity | 60 seconds |
| Failed attempts | Maximum 3 |
| Resend option | Enabled after expiry |
| Old OTP reuse | Not allowed |
| Attempt counter | Reset on resend |

---

## OTP Storage Approach

OTP information is maintained **per email address** using an in-memory structure:


Each `OtpData` object stores:
- The generated OTP
- The time at which it was created
- The number of failed verification attempts

### Why this approach?
- Fast lookup using the email as a key
- Clear separation of OTP data for each user
- Easy to reset or invalidate OTPs
- No persistence required, keeping the solution simple

This directly meets the requirement to store OTP data per email.

---

## Handled Scenarios
- Wrong OTP entered → error shown to the user
- OTP expires → verification disabled
- Too many failed attempts → resend required
- OTP resend → new OTP and timer started
- Screen rotation → no loss of OTP or session state

---

## Session Screen

Once authentication succeeds:
- The session start time is recorded
- Login duration updates live in **minutes and seconds**
- Timer continues correctly across recompositions and rotations
- Logging out clears the session and returns to the login screen

---

## State Management & Rotation Safety
- All business logic resides inside the **ViewModel**
- UI observes state changes without directly modifying logic
- `rememberSaveable` is used where UI-level state is needed
- Time values are derived dynamically, not stored

As a result, configuration changes do not reset OTP or session data.

---

## Firebase Analytics Usage

### Reason for choosing Firebase Analytics
- Easy to integrate and widely used
- No server-side setup needed
- Suitable for tracking user interaction events

### Events Tracked
- `otp_generated`
- `otp_verification_failed`
- `otp_verified`
- `logout`

Event behavior was verified using **Firebase Analytics DebugView** during testing.

---

## Running the Application
1. Open the project in Android Studio
2. Allow Gradle to sync
3. Launch an Android Emulator
4. Click ▶ **Run**
5. Make sure `google-services.json` is present in the `app/` folder

No additional configuration is needed.

---

## Demo Video
**Link:**  
https://drive.google.com/file/d/1LNdoMCA0sfFKMpIyNPkCX4vvizcZhKCV/view?usp=sharing

The video demonstrates:
- OTP generation and validation
- Expiry countdown behavior
- Handling of incorrect OTP attempts
- Session timer functionality
- Logout and analytics events

---

## Summary
This project showcases:
- Practical use of Jetpack Compose
- Clean separation of UI and business logic
- Correct OTP validation and expiry handling
- Configuration-change-safe state management
- Proper integration of Firebase Analytics without backend dependency

---

## Author
**Mohit Dayma**  
B.Tech Computer Science Engineering  
Android Developer (Kotlin)
