package dev.hossam.tawseelcompany.feature_order.data.repository

import android.util.Log
import dev.hossam.tawseelcompany.core.Localization
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_main.data.remote.ITawseelService
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.OrderDetailsDto
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.OrderDto
import dev.hossam.tawseelcompany.feature_order.domain.repository.IOrderRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class OrderRepositoryImpl constructor(
    private val api: ITawseelService
): IOrderRepository {

    private val TAG by lazy { OrderRepositoryImpl::class.java.simpleName }
    override suspend fun getOrders(): OrderDto {
        return api.getAllOrders()
    }

    override fun getOrderDetailsById(orderId: String): Flow<Resource<OrderDetailsDto>> = flow {
        try {
            emit(Resource.Loading())
            val result = api.getOrderDetailsById(orderId = orderId)
            delay(1500L)
            emit(Resource.Success(result))

        } catch (e: IOException){
            Log.i(TAG, "getOrderDetailsById: $e")
            emit(Resource.Error(Localization.CHECK_INTERNET_CONNECTION))
        } catch (e: HttpException){
            Log.i(TAG, "getOrderDetailsById: $e")
            when(e.code()){
                401 -> emit(Resource.Error(Localization.NOT_AUTHORIZED))
                else -> emit(Resource.Error(Localization.UNKNOWN_ERROR))
            }
        }
    }
}