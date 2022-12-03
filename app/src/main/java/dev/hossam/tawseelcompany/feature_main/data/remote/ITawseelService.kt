package dev.hossam.tawseelcompany.feature_main.data.remote

import dev.hossam.tawseelcompany.feature_auth.domain.model.LoginForm
import dev.hossam.tawseelcompany.feature_auth.domain.model.RegisterForm
import dev.hossam.tawseelcompany.feature_auth.domain.model.Registration
import dev.hossam.tawseelcompany.feature_auth.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface ITawseelService {

    @POST("auth/login")
    suspend fun login(@Body loginForm: LoginForm): User

    @POST("auth/register")
    suspend fun register(@Body registerForm: RegisterForm): Registration

}