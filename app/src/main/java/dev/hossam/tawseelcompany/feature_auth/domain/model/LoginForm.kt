package dev.hossam.tawseelcompany.feature_auth.domain.model

data class LoginForm(
    val phone: String = "",
    val password: String = "",
    val error: String? = null,
    val message: String? = null,
)
