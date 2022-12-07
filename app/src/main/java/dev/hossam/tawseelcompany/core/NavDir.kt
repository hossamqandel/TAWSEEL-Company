package dev.hossam.tawseelcompany.core

import dev.hossam.tawseelcompany.R

object NavDir {

    val SPLASH_TO_REGISTRATION by lazy { R.id.action_splashFragment_to_registrationFragment }
    val SPLASH_TO_HOME by lazy { R.id.action_splashFragment_to_homeFragment }
    val LOGIN_TO_HOME by lazy { R.id.action_loginFragment_to_homeFragment }
    val REGISTRATION_TO_LOGIN by lazy { R.id.action_registrationFragment_to_loginFragment }


    val HOME_TO_CREATE_ORDER by lazy { R.id.action_homeFragment_to_createOrderFragment }
    val PROFILE_TO_CHANGE_PASSWORD by lazy { R.id.action_profileFragment2_to_changePasswordFragment }
}