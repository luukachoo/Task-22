package com.example.task22.domain.remote.use_case

import javax.inject.Inject

data class HomeUseCase @Inject constructor(
    val getPostsUseCase: GetPostsUseCase,
    val getStoriesUseCase: GetStoriesUseCase
)
