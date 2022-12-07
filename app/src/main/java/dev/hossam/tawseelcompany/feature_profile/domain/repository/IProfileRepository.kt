package dev.hossam.tawseelcompany.feature_profile.domain.repository

import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_profile.domain.model.Profile
import dev.hossam.tawseelcompany.feature_profile.domain.model.UpdateProfile
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {



    fun getProfile(): Flow<Resource<Profile>>
    fun updateProfile(updateProfile: UpdateProfile): Flow<Resource<String>>
}