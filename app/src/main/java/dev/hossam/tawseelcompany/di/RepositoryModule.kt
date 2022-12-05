package dev.hossam.tawseelcompany.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hossam.tawseelcompany.feature_auth.data.repository.AuthRepositoryImpl
import dev.hossam.tawseelcompany.feature_auth.domain.repository.IAuthRepository
import dev.hossam.tawseelcompany.feature_home.data.repository.HomeRepositoryImpl
import dev.hossam.tawseelcompany.feature_home.domain.repository.IHomeRepository
import dev.hossam.tawseelcompany.feature_main.data.remote.ITawseelService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(api: ITawseelService): IAuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(api: ITawseelService): IHomeRepository {
        return HomeRepositoryImpl(api)
    }
}