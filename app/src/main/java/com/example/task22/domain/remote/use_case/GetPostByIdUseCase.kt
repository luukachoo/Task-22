package com.example.task22.domain.remote.use_case

import com.example.task22.domain.remote.repository.PostsRepository
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor(private val repository: PostsRepository) {
    suspend operator fun invoke(id: Int) =
        repository.getPostById(id)
}