package dev.hossam.tawseelcompany.feature_auth.domain.use_case.register

import dev.hossam.tawseelcompany.core.ValidationResult

class ValidateTaxRegistryNumberUseCase {

    operator fun invoke(taxRegistryNumber: String): ValidationResult {
        if (taxRegistryNumber.isBlank()){
            return ValidationResult(successful = false, errorMessage = "The tax registry number can't be empty")
        }

        return ValidationResult(successful = true)
    }
}