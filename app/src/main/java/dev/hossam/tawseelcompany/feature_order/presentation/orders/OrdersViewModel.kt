package dev.hossam.tawseelcompany.feature_order.presentation.orders

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import dev.hossam.tawseelcompany.core.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(

) : ViewModel() {

//    private val _state = MutableStateFlow(OrdersState())
//    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


}