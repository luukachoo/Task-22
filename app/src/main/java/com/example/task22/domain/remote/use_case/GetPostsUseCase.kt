package com.example.task22.domain.remote.use_case

import com.example.task22.domain.remote.repository.PostsRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postsRepository: PostsRepository) {
    suspend operator fun invoke() =
        postsRepository.getPosts()
}