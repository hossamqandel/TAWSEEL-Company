package dev.hossam.tawseelcompany.feature_auth.domain.use_case.login

data class LoginUseCases(
    val validatePhoneUseCase: ValidatePhoneUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val loginUseCase: LoginUseCase
)
