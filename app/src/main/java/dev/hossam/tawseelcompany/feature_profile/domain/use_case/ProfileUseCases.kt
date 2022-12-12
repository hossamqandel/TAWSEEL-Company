package dev.hossam.tawseelcompany.feature_profile.domain.use_case

import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.ValidatePasswordUseCase
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.register.ValidateRepeatedPasswordUseCase

data class ProfileUseCases(
    val getProfileUseCase: GetProfileUseCase,
    val updateProfileUseCase: UpdateProfileUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
    val updatePasswordUseCase: UpdatePasswordUseCase
)
