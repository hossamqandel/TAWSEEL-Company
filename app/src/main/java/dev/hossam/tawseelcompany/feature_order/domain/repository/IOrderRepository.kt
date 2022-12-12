package dev.hossam.tawseelcompany.feature_order.domain.repository

import dev.hossam.tawseelcompany.feature_order.data.remote.dto.OrdersDto

interface IOrderRepository {

    suspend fun getOrders(): OrdersDto
}