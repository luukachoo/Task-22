package com.example.task22.data.remote.network.model

import com.squareup.moshi.Json

data class StoryDto(
    @Json(name = "cover")
    val cover: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String
)