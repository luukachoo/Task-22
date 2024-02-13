package com.example.task22.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task22.data.remote.utils.Resource
import com.example.task22.domain.remote.use_case.HomeUseCase
import com.example.task22.presentation.event.HomeFragmentEvents
import com.example.task22.presentation.mapper.toPresentation
import com.example.task22.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState get() = _homeState

    fun onEvent(event: HomeFragmentEvents) {
        when (event) {
            HomeFragmentEvents.FetchPosts -> fetchPosts()
            HomeFragmentEvents.FetchStories -> fetchStories()
            HomeFragmentEvents.ResetErrorMessage -> updateErrorMessage(null)
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            homeUseCase.getPostsUseCase().collect { res ->
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
            homeUseCase.getStoriesUseCase().collect { res ->
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

    private fun updateErrorMessage(message: String?) =
        _homeState.update { it.copy(errorMessage = message) }
}