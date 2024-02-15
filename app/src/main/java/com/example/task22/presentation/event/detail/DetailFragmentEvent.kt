package com.example.task22.presentation.event.detail

sealed class DetailFragmentEvent {
    data class FetchPost(val id: Int) : DetailFragmentEvent()
    data object ResetErrorMessage : DetailFragmentEvent()
}