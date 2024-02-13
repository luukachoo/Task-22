package com.example.task22.presentation.mapper

import com.example.task22.domain.remote.model.GetOwner
import com.example.task22.presentation.model.Owner

fun GetOwner.toPresentation() = Owner(
    firstName = firstName,
    lastName = lastName,
    postDate = postDate,
    profile = profile
)