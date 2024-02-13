package com.example.task22.domain.remote.repository

import com.example.task22.data.remote.utils.Resource
import com.example.task22.domain.remote.model.GetStory
import kotlinx.coroutines.flow.Flow

interface StoriesRepository {
    suspend fun getStories(): Flow<Resource<List<GetStory>>>
}