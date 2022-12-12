package dev.hossam.tawseelcompany

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import dagger.hilt.android.HiltAndroidApp
import dev.hossam.tawseelcompany.core.Const
import dev.hossam.tawseelcompany.core.SharedPref
import java.util.*

@HiltAndroidApp
class BaseApplication : LocalizationApplication() {

    companion object {
        private lateinit var mResources: Resources
        fun getResources() = mResources
    }


    override fun onCreate() {
        super.onCreate()
        SharedPref.init(this)
        mResources = resources
        createNotificationChannel()
    }


    override fun getDefaultLanguage(context: Context): Locale {
        return Locale.ENGLISH
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                "foreground",
                "dev.hossam.tawseelcompany",
                NotificationManager.IMPORTANCE_DEFAULT)

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notificationChannel)

        }
    }
}