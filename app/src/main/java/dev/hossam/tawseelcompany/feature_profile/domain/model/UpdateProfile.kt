package dev.hossam.tawseelcompany.feature_profile.domain.model

import java.io.File

data class UpdateProfile(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val logo: File? = null
)
