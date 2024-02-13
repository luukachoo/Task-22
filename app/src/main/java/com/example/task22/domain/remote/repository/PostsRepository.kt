package com.example.task22.domain.remote.repository

import com.example.task22.data.remote.utils.Resource
import com.example.task22.domain.remote.model.GetPost
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    suspend fun getPosts(): Flow<Resource<List<GetPost>>>
}