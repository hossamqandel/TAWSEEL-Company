package dev.hossam.tawseelcompany.feature_auth.domain.use_case.register

import dev.hossam.tawseelcompany.core.ValidationResult

class ValidateRepeatedPasswordUseCase {

    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if (repeatedPassword.isBlank()){
            return ValidationResult(successful = false, errorMessage = "The repeated password can't be empty")
        }

        if (repeatedPassword != password){
            return ValidationResult(successful = false, errorMessage = "The two password fields must be matches")
        }

        return ValidationResult(successful = true)
    }
}