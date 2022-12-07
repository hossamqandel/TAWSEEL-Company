package dev.hossam.tawseelcompany.feature_profile.domain.use_case

import dev.hossam.tawseelcompany.feature_profile.domain.repository.IProfileRepository

class GetProfileUseCase constructor(
    private val repo: IProfileRepository
) {

    operator fun invoke() = repo.getProfile()
}