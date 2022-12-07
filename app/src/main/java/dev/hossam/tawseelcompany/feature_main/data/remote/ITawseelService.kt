package dev.hossam.tawseelcompany.feature_main.data.remote

import com.google.gson.JsonParser
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_auth.domain.model.LoginForm
import dev.hossam.tawseelcompany.feature_auth.domain.model.RegisterForm
import dev.hossam.tawseelcompany.feature_auth.domain.model.Registration
import dev.hossam.tawseelcompany.feature_auth.domain.model.User
import dev.hossam.tawseelcompany.feature_home.data.remote.dto.HomeDto
import dev.hossam.tawseelcompany.feature_profile.domain.model.Profile
import dev.hossam.tawseelcompany.feature_profile.domain.model.UpdateProfile
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ITawseelService {

    @POST("auth/login")
    suspend fun login(@Body loginForm: LoginForm): User

    @POST("auth/register")
    suspend fun register(@Body registerForm: RegisterForm): Registration

    @GET("home")
    suspend fun getHome(): HomeDto

    @GET("auth/me")
    suspend fun getProfile(): Profile

    @POST("auth/updateprofile")
    suspend fun updateProfile(@Body updateProfile: UpdateProfile): JsonParser

}