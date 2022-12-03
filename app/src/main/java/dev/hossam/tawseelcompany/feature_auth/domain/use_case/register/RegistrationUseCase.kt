package dev.hossam.tawseelcompany.feature_auth.domain.use_case.register

import android.util.Log
import dev.hossam.tawseelcompany.core.Const
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
            delay(500L)
            val response = repo.register(registerForm)
            emit(Resource.Success(response))
        } catch (e: IOException){
            Log.i(TAG, "invoke: $e")
            emit(Resource.Error(Const.Exception_MESSAGE_IO))
        } catch (e: HttpException){
            Log.i(TAG, "invoke: $e")
            when(e.code()){
                422 -> emit(Resource.Error("The given data was invalid"))
                else -> emit(Resource.Error(Const.Exception_MESSAGE_HTTP))
            }
        }
    }
}