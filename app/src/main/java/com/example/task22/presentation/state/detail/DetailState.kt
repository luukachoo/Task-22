package com.example.task22.presentation.state.detail

import com.example.task22.presentation.model.Post

data class DetailState(
    val post: Post? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)