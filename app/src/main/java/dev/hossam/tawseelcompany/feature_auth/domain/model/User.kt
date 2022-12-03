package dev.hossam.tawseelcompany.feature_auth.domain.model

data class User(
    val access_token: String,
    val expires_in: Int,
    val token_type: String,
    val error: String? = null,
    val message: String? = null,
)