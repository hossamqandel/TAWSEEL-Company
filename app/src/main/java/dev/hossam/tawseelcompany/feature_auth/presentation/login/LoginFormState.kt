package dev.hossam.tawseelcompany.feature_auth.presentation.login

import android.graphics.Color

data class LoginFormState(
    val phone: String = "",
    val phoneError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)
