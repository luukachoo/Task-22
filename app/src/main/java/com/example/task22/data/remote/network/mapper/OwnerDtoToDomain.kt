package com.example.task22.data.remote.network.mapper

import com.example.task22.data.remote.network.model.OwnerDto
import com.example.task22.domain.remote.model.GetOwner

fun OwnerDto.toDomain() = GetOwner(
    firstName = firstName ?: "",
    lastName = lastName ?: "",
    postDate = postDate ?: 0,
    profile = profile ?: ""
)