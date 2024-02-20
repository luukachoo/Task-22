package com.example.task22.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task22.data.remote.utils.Resource
import com.example.task22.domain.remote.use_case.GetPostsUseCase
import com.example.task22.domain.remote.use_case.GetStoriesUseCase
import com.example.task22.presentation.event.home.HomeFragmentEvents
import com.example.task22.presentation.event.home.HomeFragmentNavigationEvents
import com.example.task22.presentation.mapper.toPresentation
import com.example.task22.presentation.state.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getStoriesUseCase: GetStoriesUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState get() = _homeState

    private val _homeUiEvent = MutableSharedFlow<HomeFragmentNavigationEvents>()
    val homeUiEvent: SharedFlow<HomeFragmentNavigationEvents> get() = _homeUiEvent


    fun onEvent(event: HomeFragmentEvents) {
        viewModelScope.launch {
            when (event) {
                is HomeFragmentEvents.FetchPosts -> fetchPosts()
                is HomeFragmentEvents.FetchStories -> fetchStories()
                is HomeFragmentEvents.ResetErrorMessage -> updateErrorMessage(null)
                is HomeFragmentEvents.ItemClick -> updateNavigationEvent(
                    HomeFragmentNavigationEvents.NavigateToDetails(event.id)
                )
            }
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            getPostsUseCase().collect { res ->
                when (res) {
                    is Resource.Success -> {
                        _homeState.update { it.copy(isLoading = true) }
                        _homeState.update { state ->
                            state.copy(
                                posts = res.data.map { it.toPresentation() },
                                isLoading = false
                            )

                        }
                    }

                    is Resource.Error -> updateErrorMessage(res.errorMessage)
                }
            }
        }
    }

    private fun fetchStories() {
        viewModelScope.launch {
            getStoriesUseCase().collect { res ->
                when (res) {
                    is Resource.Success -> {
                        _homeState.update { it.copy(isLoading = true) }
                        _homeState
                            .update { state ->
                                state.copy(
                                    stories = res.data.map { it.toPresentation() },
                                    isLoading = false
                                )
                            }
                    }

                    is Resource.Error -> updateErrorMessage(res.errorMessage)
                }
            }
        }
    }

    private suspend fun updateNavigationEvent(events: HomeFragmentNavigationEvents) =
        _homeUiEvent.emit(events)


    private fun updateErrorMessage(message: String?) =
        _homeState.update { it.copy(errorMessage = message) }
}