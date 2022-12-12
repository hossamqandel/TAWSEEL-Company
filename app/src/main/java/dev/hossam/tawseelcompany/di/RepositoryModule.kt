package dev.hossam.tawseelcompany.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.hossam.tawseelcompany.BaseApplication
import dev.hossam.tawseelcompany.feature_auth.data.repository.AuthRepositoryImpl
import dev.hossam.tawseelcompany.feature_auth.domain.repository.IAuthRepository
import dev.hossam.tawseelcompany.feature_home.data.repository.HomeRepositoryImpl
import dev.hossam.tawseelcompany.feature_home.domain.repository.IHomeRepository
import dev.hossam.tawseelcompany.feature_main.data.remote.ITawseelService
import dev.hossam.tawseelcompany.feature_order.data.repository.OrderRepositoryImpl
import dev.hossam.tawseelcompany.feature_order.domain.repository.IOrderRepository
import dev.hossam.tawseelcompany.feature_profile.data.repository.ProfileRepositoryImpl
import dev.hossam.tawseelcompany.feature_profile.domain.repository.IProfileRepository
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

    @Provides
    @Singleton
    fun provideProfileRepository(api: ITawseelService): IProfileRepository {
        return ProfileRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(api: ITawseelService): IOrderRepository {
        return OrderRepositoryImpl(api)
    }


}