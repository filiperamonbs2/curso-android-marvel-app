package com.example.marvelapp.framework.di

import com.example.marvelapp.framework.network.interceptor.AuthorizationInterceptor
import com.example.marvelapp.BuildConfig
import com.example.marvelapp.framework.network.MarvelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIME_OUT = 15L

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(
                if(BuildConfig.DEBUG){
                    HttpLoggingInterceptor.Level.BODY
                } else HttpLoggingInterceptor.Level.NONE
            )
        }
    }

    @Provides
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor {
        return AuthorizationInterceptor(
            publicKey = BuildConfig.PUBLIC_KEY,
            privateKey = BuildConfig.PRIVATE_SECRET,
            calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        )
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(
            okHttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): MarvelApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(MarvelApi::class.java)
    }
}