package dev.hossam.tawseelcompany.feature_home.domain.repository

import dev.hossam.tawseelcompany.core.Resource
import dev.hossam.tawseelcompany.feature_home.data.remote.dto.HomeDto
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {


    fun getHome(): Flow<Resource<HomeDto>>
}