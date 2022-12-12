package dev.hossam.tawseelcompany.feature_order.presentation.orders

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hossam.tawseelcompany.core.DispatcherProvider
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.core.UiEvent
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.Data
import dev.hossam.tawseelcompany.feature_order.domain.use_case.OrderUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val useCases: OrderUseCases,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private var getOrdersJob: Job? = null

    private val _state = MutableStateFlow(OrdersState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init { onEvent(OrdersFilterEvent.All) }

    fun onEvent(event: OrdersFilterEvent){
        if (state.value.event::class == event::class){
            return
        }
        getOrders(event)
    }

    private fun getOrders(event: OrdersFilterEvent) {
        getOrdersJob?.cancel()
        getOrdersJob = viewModelScope.launch(dispatcher.io) {
            useCases.getOrdersUseCase(event = event).collect { resource ->
                when(resource){
                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            orders = resource.data!!,
                            event = event)
                    }

                    is Resource.Error -> {
                        resource.message?.let { errorMessage -> _uiEvent.emit(UiEvent.ShowSnackBar(errorMessage)) }
                    }
                }
            }
        }
    }
}