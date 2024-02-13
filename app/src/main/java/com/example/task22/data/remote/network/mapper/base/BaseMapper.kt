package com.example.task22.data.remote.network.mapper.base

import com.example.task22.data.remote.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun <Dto : Any, Domain : Any> Flow<Resource<Dto>>.asResource(
    onSuccess: suspend (Dto) -> Domain,
): Flow<Resource<Domain>> {
    return this.map {
        when (it) {
            is Resource.Success -> Resource.Success(data = onSuccess.invoke(it.data))
            is Resource.Error -> Resource.Error(errorMessage = it.errorMessage)
        }
    }
}