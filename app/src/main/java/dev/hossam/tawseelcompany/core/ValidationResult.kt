package dev.hossam.tawseelcompany.core

import android.graphics.Color

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
)
