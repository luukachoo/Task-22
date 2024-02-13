package com.example.task22.data.remote.repository

import com.example.task22.data.remote.network.mapper.base.asResource
import com.example.task22.data.remote.network.mapper.toDomain
import com.example.task22.data.remote.service.StoriesService
import com.example.task22.data.remote.utils.Resource
import com.example.task22.data.remote.utils.ResponseHandler
import com.example.task22.domain.remote.model.GetStory
import com.example.task22.domain.remote.repository.StoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoriesRepositoryImpl @Inject constructor(
    private val service: StoriesService,
    private val responseHandler: ResponseHandler
) : StoriesRepository {
    override suspend fun getStories(): Flow<Resource<List<GetStory>>> {
        return responseHandler.safeApiCall {
            service.getStories()
        }.asResource { list ->
            list.map {
                it.toDomain()
            }
        }
    }
}