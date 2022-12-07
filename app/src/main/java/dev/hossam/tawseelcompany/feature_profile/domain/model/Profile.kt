package dev.hossam.tawseelcompany.feature_profile.domain.model

import dev.hossam.tawseelcompany.feature_home.data.remote.dto.Company

data class Profile(
    val `data` : Company,
    val message: String? = null
)
