package com.example.task22.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task22.data.remote.utils.Resource
import com.example.task22.domain.remote.use_case.GetPostByIdUseCase
import com.example.task22.presentation.event.detail.DetailFragmentEvent
import com.example.task22.presentation.mapper.toPresentation
import com.example.task22.presentation.state.detail.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(val getPostByIdUseCase: GetPostByIdUseCase) :
    ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState get() = _detailState

    fun onEvent(event: DetailFragmentEvent) {
        when (event) {
            is DetailFragmentEvent.FetchPost -> fetchPost(event.id)
            is DetailFragmentEvent.ResetErrorMessage -> updateErrorMessage(null)
        }
    }

    private fun fetchPost(id: Int) {
        viewModelScope.launch {
            getPostByIdUseCase(id).collect { res ->
                when (res) {
                    is Resource.Success -> _detailState.update {
                        it.copy(
                            post = res.data.toPresentation(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> updateErrorMessage(res.errorMessage)
                }

            }
        }
    }

    private fun updateErrorMessage(message: String?) {
        _detailState.update { currentState -> currentState.copy(errorMessage = message) }
    }
}