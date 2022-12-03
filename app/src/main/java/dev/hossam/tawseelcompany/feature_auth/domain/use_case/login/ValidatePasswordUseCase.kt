package dev.hossam.tawseelcompany.feature_auth.domain.use_case.login

import android.graphics.Color
import dev.hossam.tawseelcompany.core.ValidationResult

class ValidatePasswordUseCase {

    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false, errorMessage = "The password can't be blank"
            )
        }

        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters"
            )
        }
        return ValidationResult(successful = true)
    }
}