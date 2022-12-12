package dev.hossam.tawseelcompany.feature_profile.presentation.change_password

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.feature_auth.presentation.register.RegistrationFormEvent
import dev.hossam.tawseelcompany.feature_profile.domain.model.UpdatePassword
import dev.hossam.tawseelcompany.feature_profile.domain.use_case.ProfileUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val useCases: ProfileUseCases,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow(UpdatePasswordState())
    val state = _state.asStateFlow()

    private val _uiText = Channel<UiText>()
    val uiText = _uiText.receiveAsFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: RegistrationFormEvent) = viewModelScope.launch(dispatcher.io){
        if (event is RegistrationFormEvent.PasswordChanged) {
            _state.value = state.value.copy(password = event.value)
        }

        if (event is RegistrationFormEvent.RepeatedPasswordChanged) {
            _state.value = state.value.copy(repeatedPassword = event.value)
        }

        if (event is RegistrationFormEvent.Submit) {
            submit()
        }
    }

    private fun submit() {
        val passwordResult = useCases.validatePasswordUseCase(state.value.password)
        val repeatedPasswordResult = useCases.validateRepeatedPasswordUseCase(
            password = state.value.password,
            repeatedPassword = state.value.repeatedPassword)

        val hasError by lazy { listOf(passwordResult, repeatedPasswordResult).any { !it.successful } }

        if (hasError){
            viewModelScope.launch(dispatcher.io){
                _state.emit(UpdatePasswordState(
                    password = state.value.password,
                    passwordError = passwordResult.errorMessage,
                    repeatedPassword = state.value.repeatedPassword,
                    repeatedPasswordError = repeatedPasswordResult.errorMessage)
                )
            }
            return
        }
        viewModelScope.launch(dispatcher.io){
            _state.emit(UpdatePasswordState(
                password = state.value.password,
                passwordError = passwordResult.errorMessage,
                repeatedPassword = state.value.repeatedPassword,
                repeatedPasswordError = repeatedPasswordResult.errorMessage)
            )
        }

        updatePassword()
    }

    private fun updatePassword() = viewModelScope.launch(dispatcher.io){
        val updatePassword by lazy { UpdatePassword(state.value.password, state.value.repeatedPassword) }
        useCases.updatePasswordUseCase(updatePassword).collectLatest { resource ->
            when(resource){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val successfulMessage by lazy { Localization.PASSWORD_UPDATED_SUCCESSFULLY }
                    _uiEvent.emit(UiEvent.ShowSnackBar(successfulMessage))
                }
                is Resource.Error -> {
                    resource.message?.let { errorMessage -> _uiEvent.emit(UiEvent.ShowSnackBar(errorMessage)) }
                }
            }
        }
    }

}