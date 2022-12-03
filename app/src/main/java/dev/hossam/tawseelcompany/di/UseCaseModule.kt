package dev.hossam.tawseelcompany.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hossam.tawseelcompany.feature_auth.domain.repository.IAuthRepository
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.LoginUseCase
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.LoginUseCases
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.ValidatePasswordUseCase
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.login.ValidatePhoneUseCase
import dev.hossam.tawseelcompany.feature_auth.domain.use_case.register.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginAuthUseCases(repository: IAuthRepository): LoginUseCases {
        return LoginUseCases(
            validatePhoneUseCase = ValidatePhoneUseCase(),
            validatePasswordUseCase = ValidatePasswordUseCase(),
            loginUseCase = LoginUseCase(repository) )
    }


    @Provides
    @Singleton
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


}