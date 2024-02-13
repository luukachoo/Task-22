package com.example.task22.data.remote.network.model

import com.squareup.moshi.Json

data class OwnerDto(
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "post_date")
    val postDate: Int?,
    @Json(name = "profile")
    val profile: String?
)