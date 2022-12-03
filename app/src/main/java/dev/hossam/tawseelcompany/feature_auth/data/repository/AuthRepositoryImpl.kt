package dev.hossam.tawseelcompany.feature_auth.data.repository

import android.util.Log
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_auth.domain.model.LoginForm
import dev.hossam.tawseelcompany.feature_auth.domain.model.RegisterForm
import dev.hossam.tawseelcompany.feature_auth.domain.model.Registration
import dev.hossam.tawseelcompany.feature_auth.domain.model.User
import dev.hossam.tawseelcompany.feature_auth.domain.repository.IAuthRepository
import dev.hossam.tawseelcompany.feature_main.data.remote.ITawseelService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ITawseelService
): IAuthRepository {

    private val TAG by lazy { AuthRepositoryImpl::class.java.simpleName }

    override suspend fun login(loginForm: LoginForm): User {
        return api.login(loginForm)
    }

    override suspend fun register(registerForm: RegisterForm): Registration {
        return api.register(registerForm)
    }
}