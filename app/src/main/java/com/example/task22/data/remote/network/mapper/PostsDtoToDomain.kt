package com.example.task22.data.remote.network.mapper

import com.example.task22.data.remote.network.model.PostDto
import com.example.task22.domain.remote.model.GetOwner
import com.example.task22.domain.remote.model.GetPost

fun PostDto.toDomain() = GetPost(
    comments = comments ?: 0,
    id = id ?: 0,
    images = images ?: listOf(),
    likes = likes ?: 0,
    getOwner = ownerDto?.toDomain() ?: GetOwner("", "", 0, ""),
    shareContent = shareContent ?: "",
    title = title ?: ""
)

