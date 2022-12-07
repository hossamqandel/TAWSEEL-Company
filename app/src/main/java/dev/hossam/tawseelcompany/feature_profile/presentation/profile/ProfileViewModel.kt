package dev.hossam.tawseelcompany.feature_profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.feature_profile.domain.use_case.ProfileUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: ProfileUseCases,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    
    init {
        getProfile()
    }
    
    
    private fun getProfile() = viewModelScope.launch(dispatcher.io){
        useCases.getProfileUseCase().collectLatest { resource ->
            when(resource){
                is Resource.Loading -> {
                    _uiEvent.emit(UiEvent.View(false))
                    _uiEvent.emit(UiEvent.Shimmer(true))
                }
                is Resource.Success -> {

                    resource.data?.let {

                        it.data.apply {
                            _state.value = state.value.copy(
                                avatar = Const.RESTAURANT_PIC,
                                name = name,
                                phoneNumber = phone,
                                email = email,
                                address = address
                            )
                        }
                    }
                    _uiEvent.emit(UiEvent.View(true))
                    _uiEvent.emit(UiEvent.Shimmer(false))
                }
                is Resource.Error -> {
                    _uiEvent.emit(UiEvent.ShowSnackBar(resource.message.toString()))
                    _uiEvent.emit(UiEvent.Shimmer(true))
                    _uiEvent.emit(UiEvent.View(false))
                }
            }
        }
    }

    fun onEvent(event: ProfileEvent) = viewModelScope.launch(dispatcher.io){
        when(event){
            is ProfileEvent.SaveProfileChanges ->
                useCases.updateProfileUseCase(event.updateProfile).collectLatest { resource ->
                when(resource){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _uiEvent.emit(UiEvent.ShowSnackBar("Profile data updated successfully"))
                    }
                    is Resource.Error -> {
                        _uiEvent.emit(UiEvent.ShowSnackBar(resource.message.toString()))
                    }
                }
            }
            is ProfileEvent.ChangePassword -> {
            val action by lazy { NavDir.PROFILE_TO_CHANGE_PASSWORD }
                _uiEvent.emit(UiEvent.Navigate(destination = action))
            }
        }
    }


}