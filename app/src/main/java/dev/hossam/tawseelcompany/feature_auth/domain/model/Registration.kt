package dev.hossam.tawseelcompany.feature_auth.domain.model
//register
//Registration
data class Registration(
    val company: Company,
    val message: String
)

data class Company(
    val id: Int,
    val address: String,
    val created_at: String,
    val email: String,
    val name: String,
    val phone: String,
    val updated_at: String
)