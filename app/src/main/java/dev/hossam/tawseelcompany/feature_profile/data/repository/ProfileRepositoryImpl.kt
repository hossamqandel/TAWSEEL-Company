package dev.hossam.tawseelcompany.feature_profile.data.repository

import android.util.JsonReader
import android.util.Log
import com.google.gson.JsonParser
import dev.hossam.tawseelcompany.core.Const
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_main.data.remote.ITawseelService
import dev.hossam.tawseelcompany.feature_profile.domain.model.Profile
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
            delay(2500L)
            val result = api.getProfile()
            emit(Resource.Success(result))

        } catch (e: IOException){
            Log.i(TAG, "getProfile: ${e.printStackTrace()}")
            emit(Resource.Error(Const.Exception_MESSAGE_IO))
        } catch (e: HttpException){
            Log.i(TAG, "getProfile: ${e.printStackTrace()}")
            when(e.code()){
                401 -> emit(Resource.Error(Const.MESSAGE_UNAUTHORIZED))
                else -> emit(Resource.Error(Const.Exception_MESSAGE_HTTP))
            }
        }
    }

    override fun updateProfile(updateProfile: UpdateProfile): Flow<Resource<String>>  = flow {
        try {
            emit(Resource.Loading())
            val result = api.updateProfile(updateProfile).parse("message").asString
            delay(500L)
            emit(Resource.Success(result))

        } catch (e: IOException){
            Log.i(TAG, "updateProfile: ${e.printStackTrace()}")
            emit(Resource.Error(Const.Exception_MESSAGE_IO))
        } catch (e: HttpException){
            Log.i(TAG, "updateProfile: ${e.printStackTrace()}")
            when(e.code()){
                401 -> emit(Resource.Error(Const.MESSAGE_UNAUTHORIZED))
                else -> emit(Resource.Error(Const.Exception_MESSAGE_HTTP))
            }
        }
    }
}