package dev.hossam.tawseelcompany.feature_profile.domain.use_case

import dev.hossam.tawseelcompany.feature_profile.domain.model.UpdateProfile
import dev.hossam.tawseelcompany.feature_profile.domain.repository.IProfileRepository

class UpdateProfileUseCase constructor(
    private val repo: IProfileRepository
) {

    operator fun invoke(updateProfile: UpdateProfile) = repo.updateProfile(updateProfile)
}