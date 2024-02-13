package com.example.task22.domain.remote.model

data class GetOwner(
    val firstName: String,
    val lastName: String,
    val postDate: Int,
    val profile: String
)