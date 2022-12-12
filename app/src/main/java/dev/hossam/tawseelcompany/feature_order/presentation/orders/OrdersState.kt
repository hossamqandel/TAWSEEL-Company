package dev.hossam.tawseelcompany.feature_order.presentation.orders

import dev.hossam.tawseelcompany.feature_order.data.remote.dto.Data

data class OrdersState(
    val orders: List<Data> = emptyList(),
    val event: OrdersFilterEvent = OrdersFilterEvent.InStock
)
