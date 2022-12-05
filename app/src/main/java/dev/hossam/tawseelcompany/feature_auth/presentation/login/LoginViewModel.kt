package dev.hossam.tawseelcompany.feature_auth.presentation.login

import android.graphics.Color
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hossam.tawseelcompany.core.DispatcherProvider
import dev.hossam.tawseelcompany.core.NavDir.LOGIN_TO_HOME
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.core.SharedPref
import dev.hossam.tawseelcompany.core.UiEvent
import dev.hossam.tawseelcompany.feature_auth.domain.model.LoginForm
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.LoginUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: LoginUseCases,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val TAG by lazy { LoginViewModel::class.java.simpleName }

    private val _state = MutableStateFlow(LoginFormState())
    val state = _state.asStateFlow()



    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

//    private val _loginEvent = Channel<LoginFormEvent>()
//    val loginEvent = _loginEvent.receiveAsFlow()


    fun onEvent(event: LoginFormEvent) {
        when(event){
            is LoginFormEvent.PhoneChanged -> _state.value = state.value.copy(phone = event.value)
            is LoginFormEvent.PasswordChanged -> _state.value = state.value.copy(password = event.value)
            is LoginFormEvent.Submit -> submit()
        }
    }

    private fun submit() {
        val phoneResult = useCases.validatePhoneUseCase(state.value.phone)
        val passwordResult = useCases.validatePasswordUseCase(state.value.password)
        val hasError = listOf(phoneResult, passwordResult).any { !it.successful }

        if (hasError){
            viewModelScope.launch {
                _state.emit(
                    LoginFormState(
                        phone = state.value.phone,
                        password = state.value.password,
                        phoneError = phoneResult.errorMessage,
                        passwordError = passwordResult.errorMessage,
                    )
                )
            }
            return
        }
        viewModelScope.launch {
            _state.emit(
                LoginFormState(
                    phone = state.value.phone,
                    password = state.value.password,
                    phoneError = phoneResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                )
            )
        }
        makeLoginRequest(LoginForm(phone = state.value.phone, password = state.value.password))
    }

    private fun makeLoginRequest(loginForm: LoginForm) = viewModelScope.launch(dispatcher.io) {
        useCases.loginUseCase(loginForm).collect { resource ->
            when(resource) {
                is Resource.Loading -> { _uiEvent.send(UiEvent.ShowSnackBar("Authenticating...")) }
                is Resource.Success -> {
                    SharedPref.setUserToken(resource.data?.access_token.toString())
                    _uiEvent.send(UiEvent.ShowSnackBar("Login Successfully"))
                    delay(1000L)
                    _uiEvent.send(UiEvent.Navigate(destination = LOGIN_TO_HOME))
                }
                is Resource.Error -> resource.message?.let {
                    _uiEvent.send(UiEvent.ShowSnackBar(it))
                }
            }
        }
    }


}