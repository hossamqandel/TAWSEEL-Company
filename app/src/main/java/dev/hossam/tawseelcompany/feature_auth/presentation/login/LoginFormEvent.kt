package dev.hossam.tawseelcompany.feature_auth.presentation.login

sealed interface LoginFormEvent {
    data class PhoneChanged(val value: String) : LoginFormEvent
    data class PasswordChanged(val value: String) : LoginFormEvent
    object Submit : LoginFormEvent
}