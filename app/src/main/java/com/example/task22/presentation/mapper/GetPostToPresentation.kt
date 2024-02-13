package com.example.task22.presentation.mapper

import com.example.task22.domain.remote.model.GetPost
import com.example.task22.presentation.model.Post

fun GetPost.toPresentation() = Post(
    comments = comments,
    id = id,
    images = images,
    likes = likes,
    owner = getOwner.toPresentation(),
    shareContent = shareContent,
    title = title
)