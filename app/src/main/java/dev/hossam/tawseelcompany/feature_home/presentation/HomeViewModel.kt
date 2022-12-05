package dev.hossam.tawseelcompany.feature_home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hossam.tawseelcompany.core.Const
import dev.hossam.tawseelcompany.core.DispatcherProvider
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.core.UiEvent
import dev.hossam.tawseelcompany.feature_home.domain.use_case.GetHomeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val dispatcher: DispatcherProvider
): ViewModel() {

    private val TAG by lazy { HomeViewModel::class.java.simpleName }

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val DEFAULT_ID by lazy { "-1" }
    private val _orderId = MutableStateFlow(DEFAULT_ID)
    val orderId = _orderId.asStateFlow()

    init {
        getHome()
    }

    private fun getHome() = viewModelScope.launch(dispatcher.io){
        getHomeUseCase().collectLatest { resource -> 
            when(resource){
                is Resource.Loading -> {
                    _uiEvent.send(UiEvent.Shimmer(true))
                    _uiEvent.send(UiEvent.View(false))
                    _uiEvent.send(UiEvent.Box(false))
                }
                is Resource.Success -> {
                    resource.data?.let { home ->
                        _orderId.value = home.last.id.toString()
                        _state.value = state.value.copy(
                            photoUrl = Const.RESTAURANT_PIC,
                            name = home.company.name,
                            address = home.company.address,
                            orderNumber = home.last.id.toString(),
                            orderStatus = home.last.status,
                            location = home.last.address_details,
                            time = home.last.date + " " + home.last.end,
                            cost = home.last.total
                        )
                    }
                    _uiEvent.send(UiEvent.Shimmer(false))
                    _uiEvent.send(UiEvent.View(true))
                    _uiEvent.send(UiEvent.Box(false))
                }

                is Resource.Error -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(resource.message.toString()))
                    _uiEvent.send(UiEvent.Shimmer(false))
                    _uiEvent.send(UiEvent.View(false))
                    _uiEvent.send(UiEvent.Box(true))
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) = viewModelScope.launch {
        when(event){
            HomeEvent.ShowDetails -> {
                Log.i(TAG, "onEvent: ${orderId.value}")
                if (orderId.value != DEFAULT_ID){
                    val direction by lazy { HomeFragmentDirections.actionHomeFragmentToOrderDetailsFragment(orderId.value) }
                    _uiEvent.send(UiEvent.Navigate(direction = direction))
                }
            }
        }
    }



}