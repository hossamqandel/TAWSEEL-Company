package dev.hossam.tawseelcompany.feature_auth.domain.model

import java.io.File

data class RegisterForm(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val logo: File? = null,
    val password: String = "",
    val password_confirmation: String = "",
)
