package dev.hossam.tawseelcompany.feature_auth.domain.use_case.register

import android.util.Log
import dev.hossam.tawseelcompany.core.Const
import dev.hossam.tawseelcompany.core.Localization
import dev.hossam.tawseelcompany.core.ResponseErrorType
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_auth.domain.model.RegisterForm
import dev.hossam.tawseelcompany.feature_auth.domain.model.Registration
import dev.hossam.tawseelcompany.feature_auth.domain.repository.IAuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class RegistrationUseCase constructor(
    private val repo: IAuthRepository
){

    companion object {
        private val TAG by lazy { RegistrationUseCase::class.java.simpleName }
    }

    operator fun invoke(registerForm: RegisterForm): Flow<Resource<Registration>> = flow {

        try {
            emit(Resource.Loading())
            val response = repo.register(registerForm)
            delay(500L)
            emit(Resource.Success(response))
        } catch (e: IOException){
            Log.i(TAG, "invoke: $e")
            emit(Resource.Error(Localization.CHECK_INTERNET_CONNECTION))
        } catch (e: HttpException){
            Log.i(TAG, "invoke: $e")
            when(e.code()){
                422 -> emit(Resource.Error(Localization.INVALID_DATA_INPUT))
                else -> emit(Resource.Error(Localization.UNKNOWN_ERROR))
            }
        }
    }
}