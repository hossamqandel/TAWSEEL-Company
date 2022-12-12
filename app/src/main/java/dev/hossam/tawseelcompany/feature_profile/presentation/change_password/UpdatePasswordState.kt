package dev.hossam.tawseelcompany.feature_profile.presentation.change_password

data class UpdatePasswordState(
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
)
