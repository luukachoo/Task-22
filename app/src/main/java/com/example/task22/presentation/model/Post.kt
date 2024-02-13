package com.example.task22.presentation.model

data class Post(
    val comments: Int,
    val id: Int,
    val images: List<String>?,
    val likes: Int,
    val owner: Owner,
    val shareContent: String,
    val title: String
)
