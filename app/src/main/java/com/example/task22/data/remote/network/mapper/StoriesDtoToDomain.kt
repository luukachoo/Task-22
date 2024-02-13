package com.example.task22.data.remote.network.mapper

import com.example.task22.data.remote.network.model.StoryDto
import com.example.task22.domain.remote.model.GetStory

fun StoryDto.toDomain() = GetStory(
    cover = cover,
    id = id,
    title = title
)