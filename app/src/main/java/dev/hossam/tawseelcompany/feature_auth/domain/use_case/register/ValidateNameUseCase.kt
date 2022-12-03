package dev.hossam.tawseelcompany.feature_auth.domain.use_case.register

import dev.hossam.tawseelcompany.core.ValidationResult

class ValidateNameUseCase {

    operator fun invoke(fullName: String): ValidationResult {
        if(fullName.isBlank())
            return ValidationResult(successful = false, errorMessage = "The name can't be empty")

        if (fullName.contains("[0-9]".toRegex()) ||
            fullName.contains("[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex()))
            return ValidationResult(successful = false, errorMessage = "The name should not contains numbers or any special characters")


        return ValidationResult(successful = true)
    }
}