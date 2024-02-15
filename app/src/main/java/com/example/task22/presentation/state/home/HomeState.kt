package com.example.task22.presentation.state.home

import com.example.task22.presentation.model.Post
import com.example.task22.presentation.model.Story

data class HomeState(
    val posts: List<Post>? = emptyList(),
    val stories: List<Story>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
