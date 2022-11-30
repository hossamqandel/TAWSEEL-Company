package dev.hossam.tawseelcompany.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.hossam.tawseelcompany.core.DispatcherProvider
import dev.hossam.tawseelcompany.core.StandardDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.hossam.tawseelcompany.core.Const
import dev.hossam.tawseelcompany.core.SharedPref
import dev.hossam.tawseelcompany.feature_main.data.remote.ITawseelService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThirdPartyLibraryModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return StandardDispatchers()
    }

    @Provides
    @Singleton
    fun provideGlide(@ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Provides
    @Singleton
    fun provideRetrofit(): ITawseelService {
        val mClient by lazy {
            OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .callTimeout(50, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(Interceptor { chain ->
                    val originalRequest = chain.request()
                    val originalUrl = originalRequest.url
                    val url = originalUrl.newBuilder().build()
                    val requestBuilder = originalRequest.newBuilder().url(url)
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer ${SharedPref.getUserToken()}")
                    val request = requestBuilder.build()
                    val response = chain.proceed(request)
                    response.code//status code
                    response
                })
                .build()
        }

        val mRetrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(mClient)
                .build().create(ITawseelService::class.java)
        }
        return mRetrofit
    }

}