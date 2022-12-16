package dev.hossam.tawseelcompany.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dev.hossam.tawseelcompany.feature_auth.domain.repository.IAuthRepository
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.LoginUseCase
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.LoginUseCases
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.ValidatePasswordUseCase
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.ValidatePhoneUseCase
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.register.*
import dev.hossam.tawseelcompany.feature_home.domain.repository.IHomeRepository
import dev.hossam.tawseelcompany.feature_home.domain.use_case.GetHomeUseCase
import dev.hossam.tawseelcompany.feature_order.domain.repository.IOrderRepository
import dev.hossam.tawseelcompany.feature_order.domain.use_case.GetOrderDetailsUseCase
import dev.hossam.tawseelcompany.feature_order.domain.use_case.GetOrdersUseCase
import dev.hossam.tawseelcompany.feature_order.domain.use_case.OrderUseCases
import dev.hossam.tawseelcompany.feature_profile.domain.repository.IProfileRepository
import dev.hossam.tawseelcompany.feature_profile.domain.use_case.GetProfileUseCase
import dev.hossam.tawseelcompany.feature_profile.domain.use_case.ProfileUseCases
import dev.hossam.tawseelcompany.feature_profile.domain.use_case.UpdatePasswordUseCase
import dev.hossam.tawseelcompany.feature_profile.domain.use_case.UpdateProfileUseCase
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideLoginAuthUseCases(repository: IAuthRepository): LoginUseCases {
        return LoginUseCases(
            validatePhoneUseCase = ValidatePhoneUseCase(),
            validatePasswordUseCase = ValidatePasswordUseCase(),
            loginUseCase = LoginUseCase(repository) )
    }


    @Provides
    @ViewModelScoped
    fun provideRegisterAuthUseCases(repository: IAuthRepository): RegisterUseCases {
        return RegisterUseCases(
            validateNameUseCase = ValidateNameUseCase(),
            validateTaxRegistryNumberUseCase = ValidateTaxRegistryNumberUseCase(),
            validatePhoneUseCase = ValidatePhoneUseCase(),
            validateEmailUseCase = ValidateEmailUseCase(),
            validatePasswordUseCase = ValidatePasswordUseCase(),
            validateRepeatedPasswordUseCase = ValidateRepeatedPasswordUseCase(),
            validateTermUseCase = ValidateTermUseCase(),
            registrationUseCase = RegistrationUseCase(repository)
            )
    }

    @Provides
    @ViewModelScoped
    fun provideGetHomeUseCase(repo: IHomeRepository): GetHomeUseCase {
        return GetHomeUseCase(repo)
    }

    @Provides
    @ViewModelScoped
    fun provideProfileUseCases(repo: IProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfileUseCase = GetProfileUseCase(repo),
            updateProfileUseCase = UpdateProfileUseCase(repo),
            validatePasswordUseCase = ValidatePasswordUseCase(),
            validateRepeatedPasswordUseCase = ValidateRepeatedPasswordUseCase(),
            updatePasswordUseCase = UpdatePasswordUseCase(repo)
        )
    }

    @Provides
    @ViewModelScoped
    fun provideOrderUseCases(repo: IOrderRepository): OrderUseCases {
        return OrderUseCases(
            getOrdersUseCase = GetOrdersUseCase(repo),
            getOrderDetailsUseCase = GetOrderDetailsUseCase(repo)
        )
    }


}