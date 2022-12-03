package dev.hossam.tawseelcompany.feature_auth.domain.use_case.register

import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.ValidatePasswordUseCase
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.ValidatePhoneUseCase

data class RegisterUseCases(
    val validateNameUseCase: ValidateNameUseCase,
    val validateTaxRegistryNumberUseCase: ValidateTaxRegistryNumberUseCase,
    val validatePhoneUseCase: ValidatePhoneUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
    val validateTermUseCase: ValidateTermUseCase,
    val registrationUseCase: RegistrationUseCase
)
