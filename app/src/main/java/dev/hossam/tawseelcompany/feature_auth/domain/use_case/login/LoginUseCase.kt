package dev.hossam.tawseelcompany.feature_auth.domain.use_case.login

import android.util.Log
import dev.hossam.tawseelcompany.core.Const
import dev.hossam.tawseelcompany.core.Const.MESSAGE_UNAUTHORIZED
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_auth.domain.model.LoginForm
import dev.hossam.tawseelcompany.feature_auth.domain.model.User
import dev.hossam.tawseelcompany.feature_auth.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class LoginUseCase (
    private val repo: IAuthRepository
) {

    private val TAG by lazy { LoginUseCase::class.java.simpleName }

    operator fun invoke(loginForm: LoginForm): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val result = repo.login(loginForm = loginForm)
            if (result.error != null || result.message != null) {
                emit(Resource.Error(result.message.toString()))
            }
            else emit(Resource.Success(result))

        } catch (e: IOException){
            Log.i(TAG, "invoke: $e")
            emit(Resource.Error(Const.Exception_MESSAGE_IO))
        } catch (e: HttpException){
            Log.i(TAG, "invoke: $e")
            when(e.code()){
                401 -> emit(Resource.Error(MESSAGE_UNAUTHORIZED))
                else -> emit(Resource.Error(Const.Exception_MESSAGE_HTTP))
            }
        }
    }



}