package com.example.task22.presentation.mapper

import com.example.task22.domain.remote.model.GetStory
import com.example.task22.presentation.model.Story

fun GetStory.toPresentation() = Story(
    cover = cover,
    id = id,
    title = title
)