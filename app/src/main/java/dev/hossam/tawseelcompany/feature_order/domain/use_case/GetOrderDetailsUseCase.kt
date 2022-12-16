package dev.hossam.tawseelcompany.feature_order.domain.use_case

import dev.hossam.tawseelcompany.feature_order.domain.repository.IOrderRepository

class GetOrderDetailsUseCase constructor(
    private val repo: IOrderRepository
) {

    operator fun invoke(orderId: String) = repo.getOrderDetailsById(orderId = orderId)
}