package dev.hossam.tawseelcompany.feature_auth.domain.use_case.login

import android.graphics.Color
import dev.hossam.tawseelcompany.core.ValidationResult

class ValidatePhoneUseCase {

    operator fun invoke(phone: String): ValidationResult {
        if (phone.isBlank()) {
            return  ValidationResult(
                successful = false, errorMessage = "The phone can't be blank"
            )
        }
        if (phone.length != 11) {
            return ValidationResult(
                successful = false,
                errorMessage = "The phone should be contains 11 numbers",
            )
        }
        return ValidationResult(successful = true)
    }
}