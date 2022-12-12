package dev.hossam.tawseelcompany.feature_profile.data.repository

import android.util.JsonReader
import android.util.Log
import com.google.gson.JsonParser
import dev.hossam.tawseelcompany.R
import dev.hossam.tawseelcompany.core.*
import dev.hossam.tawseelcompany.core.Const.JSON_KEY_MESSAGE
import dev.hossam.tawseelcompany.feature_main.data.remote.ITawseelService
import dev.hossam.tawseelcompany.feature_profile.domain.model.Profile
import dev.hossam.tawseelcompany.feature_profile.domain.model.UpdatePassword
import dev.hossam.tawseelcompany.feature_profile.domain.model.UpdateProfile
import dev.hossam.tawseelcompany.feature_profile.domain.repository.IProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException

class ProfileRepositoryImpl constructor(
    private val api: ITawseelService
): IProfileRepository {

    private val TAG by lazy { ProfileRepositoryImpl::class.java.simpleName }

    override fun getProfile(): Flow<Resource<Profile>> = flow {
        try {
            emit(Resource.Loading())
            val result = api.getProfile()
            delay(1500L)
            emit(Resource.Success(result))

        } catch (e: IOException){
            Log.i(TAG, "getProfile: ${e.printStackTrace()}")
            emit(Resource.Error(Localization.CHECK_INTERNET_CONNECTION))
        } catch (e: HttpException){
            Log.i(TAG, "getProfile: ${e.printStackTrace()}")
            when(e.code()){
                401 -> emit(Resource.Error(Localization.NOT_AUTHORIZED))
                else -> emit(Resource.Error(Localization.UNKNOWN_ERROR))
            }
        }
    }

    override fun updateProfile(updateProfile: UpdateProfile): Flow<Resource<String>>  = flow {
        try {
            emit(Resource.Loading())
            delay(2000L)
            val result = api.updateProfile(updateProfile).parse("message").asString
            emit(Resource.Success(result))

        } catch (e: IOException){
            Log.i(TAG, "updateProfile: ${e.printStackTrace()}")
            emit(Resource.Error(Localization.CHECK_INTERNET_CONNECTION))
        } catch (e: HttpException){
            Log.i(TAG, "updateProfile: ${e.printStackTrace()}")
            when(e.code()){
                401 -> emit(Resource.Error(Localization.NOT_AUTHORIZED))
                else -> emit(Resource.Error(Localization.UNKNOWN_ERROR))
            }
        }
    }

    override suspend fun updatePassword(updatePassword: UpdatePassword): JsonParser {
        return api.updatePassword(updatePassword)
    }
}