package com.example.task22.presentation.screen.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.task22.databinding.FragmentHomeBinding
import com.example.task22.presentation.event.home.HomeFragmentEvents
import com.example.task22.presentation.event.home.HomeFragmentNavigationEvents
import com.example.task22.presentation.extension.showSnackbar
import com.example.task22.presentation.helper.Listener
import com.example.task22.presentation.helper.Observer
import com.example.task22.presentation.screen.base.BaseFragment
import com.example.task22.presentation.screen.home.post.PostRecyclerAdapter
import com.example.task22.presentation.screen.home.story.StoryRecyclerAdapter
import com.example.task22.presentation.state.home.HomeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), Observer,
    Listener {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val storiesAdapter by lazy { StoryRecyclerAdapter() }
    private val postsAdapter by lazy { PostRecyclerAdapter() }

    override fun init() {
        listeners()
        setUpRecyclers()
        observers()
    }

    override fun listeners() {
        postsAdapter.onClick { x ->
            viewModel.onEvent(HomeFragmentEvents.ItemClick(x.id))
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeState.collect {
                    handleHomeState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeUiEvent.collect {
                    handleNavigationEvent(it)
                }
            }
        }
    }

    private fun setUpRecyclers() = with(binding) {
        rvStories.adapter = storiesAdapter
        viewModel.onEvent(HomeFragmentEvents.FetchStories)

        rvPosts.adapter = postsAdapter
        viewModel.onEvent(HomeFragmentEvents.FetchPosts)
    }


    private fun handleHomeState(state: HomeState) {
        binding.progressBar.isVisible = state.isLoading

        state.posts?.let {
            postsAdapter.submitList(it)
        }

        state.stories?.let {
            storiesAdapter.submitList(it)
        }

        state.errorMessage?.let {
            binding.root.showSnackbar(message = it)
            viewModel.onEvent(HomeFragmentEvents.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvent(event: HomeFragmentNavigationEvents) {
        when (event) {
            is HomeFragmentNavigationEvents.NavigateToDetails -> findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(event.id)
            )
        }
    }
}