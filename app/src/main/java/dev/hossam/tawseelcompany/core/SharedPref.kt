package dev.hossam.tawseelcompany.core

import android.content.Context
import android.content.SharedPreferences

object SharedPref {

    private var mAppContext: Context? = null
    private const val SHARED_PREFERENCES_NAME = "tawseel data"
    private const val USER_NAME = "name"
    private const val USER_PHONE = "phone"
    private const val USER_EMAIL = "email"
    private const val USER_ADDRESS = "address"
    private const val USER_LOGO = "logo"
    private const val USER_UPDATED_AT = "updated_at"
    private const val USER_CREATED_AT = "created_at"
    private const val USER_ID = "id"
    private const val APP_Language = "language"
    private const val USER_TOKEN = "token"
    private const val USER_PASSWORD = "password"


    fun init(appContext: Context?) {
        mAppContext = appContext
    }

    private fun getSharedPreferences(): SharedPreferences {
        return mAppContext!!.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setUserToken (value : String){
        val editor = getSharedPreferences().edit()
        editor.putString(USER_TOKEN, value).apply()
    } fun getUserToken ():String = getSharedPreferences().getString(USER_TOKEN , "")!!


    fun setUserName (value : String){
        val editor = getSharedPreferences().edit()
        editor.putString(USER_NAME, value).apply()
    } fun getUserName ():String = getSharedPreferences().getString(USER_NAME , "")!!

    fun setUserPhone (value : String){
        val editor = getSharedPreferences().edit()
        editor.putString(USER_PHONE, value).apply()
    } fun getUserPhone ():String = getSharedPreferences().getString(USER_PHONE , "")!!


    fun setUserEmail (value : String){
        val editor = getSharedPreferences().edit()
        editor.putString(USER_EMAIL, value).apply()
    } fun getUserEmail ():String = getSharedPreferences().getString(USER_EMAIL , "")!!

    fun setUserAddress (value : String){
        val editor = getSharedPreferences().edit()
        editor.putString(USER_ADDRESS, value).apply()
    } fun getUserAddress ():String = getSharedPreferences().getString(USER_ADDRESS , "")!!
}