package com.example.task22.domain.remote.model

data class GetPost(
    val comments: Int,
    val id: Int,
    val images: List<String>,
    val likes: Int,
    val getOwner: GetOwner,
    val shareContent: String,
    val title: String
)