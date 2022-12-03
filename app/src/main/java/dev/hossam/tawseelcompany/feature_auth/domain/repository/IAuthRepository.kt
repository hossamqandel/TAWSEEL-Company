package dev.hossam.tawseelcompany.feature_auth.domain.repository

import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_auth.domain.model.LoginForm
import dev.hossam.tawseelcompany.feature_auth.domain.model.RegisterForm
import dev.hossam.tawseelcompany.feature_auth.domain.model.Registration
import dev.hossam.tawseelcompany.feature_auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    suspend fun login(loginForm: LoginForm): User
    suspend fun register(registerForm: RegisterForm): Registration
}