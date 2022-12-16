package dev.hossam.tawseelcompany.feature_order.domain.repository

import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.OrderDetailsDto
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.OrderDto
import kotlinx.coroutines.flow.Flow

interface IOrderRepository {

    suspend fun getOrders(): OrderDto
    fun getOrderDetailsById(orderId: String): Flow<Resource<OrderDetailsDto>>
}