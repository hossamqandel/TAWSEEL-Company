package dev.hossam.tawseelcompany.feature_order.presentation.order_details

data class OrderState(
    val orderNumber: String = "",
    val orderStatus: String = "",
    val address: String = "",
    val time: String = "",
    val cost: String = "",
    val isCancelled: Boolean = true,
    val cancellationReason: String = "",
    val customerName: String = "",
    val customerPhone: String = "",
)
