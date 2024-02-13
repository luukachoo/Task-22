package com.example.task22.data.remote.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ResponseHandler @Inject constructor() {
    fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Error Code: ${response.code()}"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Network Error: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Server Error ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Uknown Error ${e.message}"))
        }
    }
}