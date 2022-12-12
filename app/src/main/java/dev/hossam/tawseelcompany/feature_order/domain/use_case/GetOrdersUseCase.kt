package dev.hossam.tawseelcompany.feature_order.domain.use_case

import android.util.Log
import dev.hossam.tawseelcompany.core.Const
import dev.hossam.tawseelcompany.core.Localization
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.Data
import dev.hossam.tawseelcompany.feature_order.data.remote.dto.OrdersDto
import dev.hossam.tawseelcompany.feature_order.domain.repository.IOrderRepository
import dev.hossam.tawseelcompany.feature_order.presentation.orders.OrdersFilterEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetOrdersUseCase constructor(
    private val repo: IOrderRepository
) {
    private val TAG by lazy { GetOrdersUseCase::class.java.simpleName }

    operator fun invoke(event: OrdersFilterEvent): Flow<Resource<List<Data>>> = flow {
        try {
            emit(Resource.Loading())
            delay(800L)

            when(event){
                OrdersFilterEvent.All -> emit(Resource.Success(getAll()))
                OrdersFilterEvent.InStock -> emit(Resource.Success(getInStock()))
                OrdersFilterEvent.Started -> emit(Resource.Success(getStarted()))
                OrdersFilterEvent.Completed -> emit(Resource.Success(getCompleted()))
                OrdersFilterEvent.Canceled -> emit(Resource.Success(getCanceled()))
                OrdersFilterEvent.Refused -> emit(Resource.Success(getRefused()))
            }
        } catch (e: IOException){
            Log.i(TAG, "invoke: $e")
            emit(Resource.Error(Localization.CHECK_INTERNET_CONNECTION))
        } catch (e: HttpException){
            Log.i(TAG, "invoke: $e")
            //TODO
        }
    }

    private suspend fun getAll() = repo.getOrders().data
    private suspend fun getCompleted() = repo.getOrders().data.filter { it.status == Const.COMPLETED }
    private suspend fun getRefused() = repo.getOrders().data.filter { it.status == Const.REFUSED }
    private suspend fun getStarted() = repo.getOrders().data.filter { it.status == Const.STARTED }
    private suspend fun getInStock() = repo.getOrders().data.filter { it.status == Const.IN_STOCK }
    private suspend fun getCanceled() = repo.getOrders().data.filter { it.status == Const.CANCELLED }
}