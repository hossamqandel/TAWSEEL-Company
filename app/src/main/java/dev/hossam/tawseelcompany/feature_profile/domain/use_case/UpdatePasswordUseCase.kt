package dev.hossam.tawseelcompany.feature_profile.domain.use_case

import android.util.Log
import dev.hossam.tawseelcompany.core.Const
import dev.hossam.tawseelcompany.core.Localization
import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_profile.domain.model.UpdatePassword
import dev.hossam.tawseelcompany.feature_profile.domain.repository.IProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class UpdatePasswordUseCase constructor(
    private val repo: IProfileRepository
) {

    companion object {
        private val TAG by lazy { UpdatePasswordUseCase::class.java.simpleName }
    }

    operator fun invoke(updatePassword: UpdatePassword): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val responseResult = repo.updatePassword(updatePassword = updatePassword).parse("message").asString
            emit(Resource.Success(responseResult))
        } catch (e: IOException){
            Log.i(TAG, "updatePassword: $e")
            emit(Resource.Error(Localization.CHECK_INTERNET_CONNECTION))
        } catch (e: HttpException){
            Log.i(TAG, "updatePassword: $e")
            when(e.code()){
                401 -> emit(Resource.Error(Localization.NOT_AUTHORIZED))
                else -> emit(Resource.Error(Localization.UNKNOWN_ERROR))
            }
        }
    }
}