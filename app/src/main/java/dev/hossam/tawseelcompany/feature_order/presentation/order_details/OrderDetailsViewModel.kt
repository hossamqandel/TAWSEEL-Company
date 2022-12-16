package dev.hossam.tawseelcompany.feature_order.presentation.order_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hossam.tawseelcompany.core.DispatcherProvider
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.core.UiEvent
import dev.hossam.tawseelcompany.feature_order.domain.use_case.OrderUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val useCases: OrderUseCases,
    private val dispatcher: DispatcherProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val TAG by lazy { OrderDetailsViewModel::class.java.simpleName }

    private val _orderId = MutableStateFlow("-1")
    private val orderId = _orderId.asStateFlow()

    init {
        savedStateHandle.get<String>("orderId")?.let { orderId ->
            if (orderId != "-1"){
                _orderId.value = orderId
            }
        }
        getOrderDetails()
    }

    private val _orderState = MutableStateFlow(OrderState())
    val orderState = _orderState.asStateFlow()

    private val _itemsState = MutableStateFlow(ItemsState())
    val itemsState = _itemsState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    private fun getOrderDetails() = viewModelScope.launch(dispatcher.io){
        Log.i(TAG, "getOrderDetails: orderId --> ${orderId.value}")

        useCases.getOrderDetailsUseCase(orderId = orderId.value).collectLatest { resource ->
            when(resource) {
                is Resource.Loading -> {
                    _uiEvent.emit(UiEvent.View(false))
                    _uiEvent.emit(UiEvent.Shimmer(true))
                }

                is Resource.Success -> {
                resource.data?.let { orderInfo ->
                    orderInfo.data.apply {
                        Log.i(TAG, "getOrderDetails shipping: $shipping")
                        Log.i(TAG, "getOrderDetails Data: \n\n\n\n${orderInfo.data}")
                        _itemsState.value = itemsState.value.copy(items = items, shippingCost = shipping)

                        _orderState.value = orderState.value.copy(
                            orderNumber = id.toString(),
                            orderStatus = status,
                            address = address_details,
                            time = created_at,
                            cost = total,
                            isCancelled = if (status == "cancelled") true else false,
                            cancellationReason = (reasons ?: "No reason") as String,
                            customerName = client,
                            customerPhone = phone,
                            )
                        }
                    }

                    _uiEvent.emit(UiEvent.Shimmer(false))
                    _uiEvent.emit(UiEvent.View(true))

                }

                is Resource.Error -> {
                    _uiEvent.emit(UiEvent.Shimmer(true))
                    _uiEvent.emit(UiEvent.View(false))
                    resource.message?.let { errorMessage -> _uiEvent.emit(UiEvent.ShowSnackBar(errorMessage)) }
                }
            }
        }
    }


}