package com.example.task22.data.remote.repository

import com.example.task22.data.remote.network.mapper.base.asResource
import com.example.task22.data.remote.network.mapper.toDomain
import com.example.task22.data.remote.service.PostsService
import com.example.task22.data.remote.utils.Resource
import com.example.task22.data.remote.utils.ResponseHandler
import com.example.task22.domain.remote.model.GetPost
import com.example.task22.domain.remote.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val service: PostsService,
    private val responseHandler: ResponseHandler
) : PostsRepository {
    override suspend fun getPosts(): Flow<Resource<List<GetPost>>> {
        return responseHandler.safeApiCall {
            service.getPosts()
        }.asResource {
            it.map { dto ->
                dto.toDomain()
            }
        }
    }
}