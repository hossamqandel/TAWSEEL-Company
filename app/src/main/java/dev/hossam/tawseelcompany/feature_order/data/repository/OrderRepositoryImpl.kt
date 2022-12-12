package dev.hossam.tawseelcompany.feature_order.data.repository

import dev.hossam.tawseelcompany.feature_main.data.remote.ITawseelService
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.OrdersDto
import dev.hossam.tawseelcompany.feature_order.domain.repository.IOrderRepository

class OrderRepositoryImpl constructor(
    private val api: ITawseelService
): IOrderRepository {

    override suspend fun getOrders(): OrdersDto {
        return api.getAllOrders()
    }
}