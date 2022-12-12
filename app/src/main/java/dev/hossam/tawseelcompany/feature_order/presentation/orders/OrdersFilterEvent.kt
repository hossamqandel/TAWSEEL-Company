package dev.hossam.tawseelcompany.feature_order.presentation.orders

sealed interface OrdersFilterEvent {
    object All : OrdersFilterEvent
    object Completed: OrdersFilterEvent
    object Refused: OrdersFilterEvent
    object InStock: OrdersFilterEvent
    object Started: OrdersFilterEvent
    object Canceled: OrdersFilterEvent
}