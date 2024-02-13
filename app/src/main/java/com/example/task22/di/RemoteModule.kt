package com.example.task22.di

import com.example.task22.BuildConfig
import com.example.task22.data.remote.service.PostsService
import com.example.task22.data.remote.service.StoriesService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideHttpLogginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(httpClient: OkHttpClient): Retrofit {
        val moshi = MoshiConverterFactory.create(
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        )
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(moshi)
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providePostsServie(retrofit: Retrofit): PostsService {
        return retrofit.create(PostsService::class.java)
    }

    @Provides
    @Singleton
    fun provideStoriesService(retrofit: Retrofit): StoriesService {
        return retrofit.create(StoriesService::class.java)
    }

}