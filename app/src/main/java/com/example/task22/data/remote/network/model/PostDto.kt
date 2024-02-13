package com.example.task22.data.remote.network.model

import com.squareup.moshi.Json

data class PostDto(
    @Json(name = "comments")
    val comments: Int?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "images")
    val images: List<String>?,
    @Json(name = "likes")
    val likes: Int?,
    @Json(name = "owner")
    val ownerDto: OwnerDto?,
    @Json(name = "share_content")
    val shareContent: String?,
    @Json(name = "title")
    val title: String?
)