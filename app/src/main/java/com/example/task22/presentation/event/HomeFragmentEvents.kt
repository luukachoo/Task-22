package com.example.task22.presentation.event

sealed class HomeFragmentEvents {
    data object FetchStories : HomeFragmentEvents()
    data object FetchPosts : HomeFragmentEvents()
    data object ResetErrorMessage : HomeFragmentEvents()
}