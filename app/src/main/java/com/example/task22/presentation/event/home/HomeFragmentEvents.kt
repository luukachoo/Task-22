package com.example.task22.presentation.event.home

sealed class HomeFragmentEvents {
    data object FetchStories : HomeFragmentEvents()
    data object FetchPosts : HomeFragmentEvents()
    data object ResetErrorMessage : HomeFragmentEvents()
    data class ItemClick(val id: Int) : HomeFragmentEvents()
}