package dev.hossam.tawseelcompany.feature_auth.presentation.register

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.DispatcherProvider
import dev.hossam.tawseelcompany.core.NavDir
import dev.hossam.tawseelcompany.core.NavDir.REGISTRATION_TO_LOGIN
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.core.UiEvent
import dev.hossam.tawseelcompany.feature_auth.domain.model.RegisterForm
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.register.RegisterUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val useCases: RegisterUseCases,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val TAG by lazy { RegistrationViewModel::class.java.simpleName }

    private val addresses by lazy { listOf("Dokki, Cairo", "Giza, Al-Haram", "Tahrir Square, Cairo") }
    private val _state = MutableStateFlow(RegisterFormState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()




    fun onEvent(event: RegistrationFormEvent){
        when(event){
            is RegistrationFormEvent.NameChanged -> _state.value = state.value.copy(fullName = event.value)
            is RegistrationFormEvent.TaxRegistryNumberChanged -> _state.value = state.value.copy(taxRegistryNumber = event.value)
            is RegistrationFormEvent.PhoneNumberChanged -> _state.value = state.value.copy(phone = event.value)
            is RegistrationFormEvent.EmailChanged -> _state.value = state.value.copy(email = event.value)
            is RegistrationFormEvent.PasswordChanged -> _state.value = state.value.copy(password = event.value)
            is RegistrationFormEvent.RepeatedPasswordChanged -> _state.value = state.value.copy(repeatedPassword = event.value)
            is RegistrationFormEvent.AcceptTerms -> _state.value = state.value.copy(acceptedTerms = event.isAccepted)
            is RegistrationFormEvent.Submit -> submit()
        }
    }

    private fun submit() {
        val nameResult = useCases.validateNameUseCase(state.value.fullName)
        val taxRegistryNum = useCases.validateTaxRegistryNumberUseCase(state.value.taxRegistryNumber)
        val phoneResult = useCases.validatePhoneUseCase(state.value.phone)
        val emailResult = useCases.validateEmailUseCase(state.value.email)
        val passwordResult = useCases.validatePasswordUseCase(state.value.password)
        val repeatedPasswordResult = useCases.validateRepeatedPasswordUseCase(state.value.password, state.value.repeatedPassword)
        val acceptTermsResult = useCases.validateTermUseCase(state.value.acceptedTerms)
        val hasError = listOf(nameResult, taxRegistryNum, phoneResult, emailResult, passwordResult, repeatedPasswordResult,
            acceptTermsResult
        ).any { !it.successful }

        if (hasError){
            viewModelScope.launch {
                _state.emit(
                    RegisterFormState(
                        fullName = state.value.fullName,
                        taxRegistryNumber = state.value.taxRegistryNumber,
                        phone = state.value.phone,
                        email = state.value.email,
                        password = state.value.password,
                        repeatedPassword = state.value.repeatedPassword,
                        acceptedTerms = state.value.acceptedTerms,
                        fullNameError = nameResult.errorMessage,
                        taxRegistryNumberError = taxRegistryNum.errorMessage,
                        phoneError = phoneResult.errorMessage,
                        emailError = emailResult.errorMessage,
                        passwordError = passwordResult.errorMessage,
                        repeatedPasswordError = repeatedPasswordResult.errorMessage,
                    )
                )
                acceptTermsResult.errorMessage?.let { _uiEvent.send(UiEvent.ShowSnackBar(it)) }
            }
            return
        }

        viewModelScope.launch {
            _state.emit(
                RegisterFormState(
                    fullName = state.value.fullName,
                    taxRegistryNumber = state.value.taxRegistryNumber,
                    phone = state.value.phone,
                    email = state.value.email,
                    password = state.value.password,
                    repeatedPassword = state.value.repeatedPassword,
                    acceptedTerms = state.value.acceptedTerms,
                    fullNameError = nameResult.errorMessage,
                    taxRegistryNumberError = taxRegistryNum.errorMessage,
                    phoneError = phoneResult.errorMessage,
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                    repeatedPasswordError = repeatedPasswordResult.errorMessage,
                )
            )
        }
        val value = state.value
        val registerForm = RegisterForm(
            name = value.fullName,
            phone = value.phone,
            email = value.email,
            address = addresses.random(),
            logo = null,
            password = value.password,
            password_confirmation = value.repeatedPassword
            )
        makeRegistrationRequest(registerForm)
    }

    private fun makeRegistrationRequest(registerForm: RegisterForm) = viewModelScope.launch(dispatcher.io){
        useCases.registrationUseCase(registerForm).collectLatest { resource ->
            when(resource){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _uiEvent.send(UiEvent.ShowSnackBar("Company successfully register, you can login now"))
                }
                is Resource.Error -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(resource.message.toString()))
                }
            }
        }
    }

}