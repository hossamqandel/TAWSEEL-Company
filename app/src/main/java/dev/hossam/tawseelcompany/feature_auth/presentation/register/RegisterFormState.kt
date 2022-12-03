package dev.hossam.tawseelcompany.feature_auth.presentation.register

data class RegisterFormState(
    val fullName: String = "",
    val fullNameError: String? = null,
    val taxRegistryNumber: String = "",
    val taxRegistryNumberError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    val acceptedTerms: Boolean = false,
    val termsError: String? = null

)
