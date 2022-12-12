package dev.hossam.tawseelcompany.feature_home.data.repository

import android.util.Log
import dev.hossam.tawseelcompany.core.Const
import dev.hossam.tawseelcompany.core.Localization
import dev.hossam.tawseelcompany.core.ResponseErrorType
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_home.data.remote.dto.HomeDto
import dev.hossam.tawseelcompany.feature_home.domain.repository.IHomeRepository
import dev.hossam.tawseelcompany.feature_main.data.remote.ITawseelService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class HomeRepositoryImpl constructor(
    private val api: ITawseelService
): IHomeRepository {

    private val TAG by lazy { this::class.java.simpleName }

    override fun getHome(): Flow<Resource<HomeDto>> = flow {
        try {
            emit(Resource.Loading())
            val result = api.getHome()
            delay(1200L)
            emit(Resource.Success(result))
        } catch (e: IOException){
            Log.i(TAG, "getHome: $e")
            emit(Resource.Error(Localization.CHECK_INTERNET_CONNECTION))
        } catch (e: HttpException){
            Log.i(TAG, "getHome: $e")
            emit(Resource.Error(Localization.UNKNOWN_ERROR))
        }
    }
}