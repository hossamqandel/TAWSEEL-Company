package dev.hossam.tawseelcompany.feature_order.presentation.order_details

import dev.hossam.tawseelcompany.feature_order.data.remote.dto.Data
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.Item

data class ItemsState(
    val items: List<Item> = emptyList(),
    val shippingCost: String? = "0"
)
