package dev.hossam.tawseelcompany.feature_auth.presentation.register

sealed interface RegistrationFormEvent {
    data class NameChanged(val value: String) : RegistrationFormEvent
    data class TaxRegistryNumberChanged(val value: String) : RegistrationFormEvent
    data class PhoneNumberChanged(val value: String) : RegistrationFormEvent
    data class EmailChanged(val value: String) : RegistrationFormEvent
    data class PasswordChanged(val value: String) : RegistrationFormEvent
    data class RepeatedPasswordChanged(val value: String) : RegistrationFormEvent
    data class AcceptTerms(val isAccepted: Boolean) : RegistrationFormEvent
    object Submit : RegistrationFormEvent
}
