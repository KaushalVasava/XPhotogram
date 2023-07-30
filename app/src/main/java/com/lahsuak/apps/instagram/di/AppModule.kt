package com.lahsuak.apps.instagram.di

import com.lahsuak.apps.instagram.api.InstagramApi
import com.lahsuak.apps.instagram.repos.HomeRepo
import com.lahsuak.apps.instagram.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideInstagramApi(retrofit: Retrofit): InstagramApi = retrofit.create(InstagramApi::class.java)

    @Provides
    @Singleton
    fun providerInstagramRepo(api: InstagramApi) = HomeRepo(api)
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope