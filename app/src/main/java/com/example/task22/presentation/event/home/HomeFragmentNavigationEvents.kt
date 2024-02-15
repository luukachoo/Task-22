package com.example.task22.presentation.event.home

sealed interface HomeFragmentNavigationEvents {
    data class NavigateToDetails(val id: Int) : HomeFragmentNavigationEvents
}