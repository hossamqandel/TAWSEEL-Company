package dev.hossam.tawseelcompany.feature_auth.domain.use_case.register

import dev.hossam.tawseelcompany.core.ValidationResult

class ValidateTermUseCase {

    operator fun invoke(acceptedTerms: Boolean): ValidationResult {
        if (!acceptedTerms){
            return ValidationResult(successful = false, errorMessage = "You should accept terms and conditions")
        }
        return ValidationResult(successful = true)
    }
}