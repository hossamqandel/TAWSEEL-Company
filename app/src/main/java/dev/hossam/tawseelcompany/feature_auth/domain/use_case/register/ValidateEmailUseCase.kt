package dev.hossam.tawseelcompany.feature_auth.domain.use_case.register

import dev.hossam.tawseelcompany.core.ValidationResult
import java.util.regex.Pattern

class ValidateEmailUseCase {

    companion object {
        private fun validateEmail(email: String): Boolean {
            val emailRegex by lazy { "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$" }
            val emailPattern by lazy { Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE) }
            val matcher = emailPattern.matcher(email)
            return matcher.find()
        }
    }

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()){
            return ValidationResult(successful = false, errorMessage = "The email address can't be empty")
        }

        if (!validateEmail(email)) {
            return ValidationResult(successful = false, errorMessage = "Please enter valid Email")
        }



        return ValidationResult(successful = true)
    }



}