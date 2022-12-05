package dev.hossam.tawseelcompany.feature_home.domain.use_case

import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_home.data.remote.dto.HomeDto
import dev.hossam.tawseelcompany.feature_home.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow

class GetHomeUseCase constructor(
    private val repo: IHomeRepository
) {

    operator fun invoke(): Flow<Resource<HomeDto>> = repo.getHome()
}