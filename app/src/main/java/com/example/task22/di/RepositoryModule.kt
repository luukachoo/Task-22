package com.example.task22.di

import com.example.task22.data.remote.repository.PostsRepositoryImpl
import com.example.task22.data.remote.repository.StoriesRepositoryImpl
import com.example.task22.domain.remote.repository.PostsRepository
import com.example.task22.domain.remote.repository.StoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindStoriesRepository(storiesRepositoryImpl: StoriesRepositoryImpl): StoriesRepository

    @Binds
    @Singleton
    fun bindPostsRepository(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository
}