package com.example.task22.presentation.screen.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.task22.databinding.FragmentHomeBinding
import com.example.task22.presentation.event.HomeFragmentEvents
import com.example.task22.presentation.extension.showSnackbar
import com.example.task22.presentation.helper.Observer
import com.example.task22.presentation.screen.base.BaseFragment
import com.example.task22.presentation.screen.home.post.PostRecyclerAdapter
import com.example.task22.presentation.screen.home.story.StoryRecyclerAdapter
import com.example.task22.presentation.state.HomeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), Observer {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val storiesAdapter by lazy { StoryRecyclerAdapter() }
    private val postsAdapter by lazy { PostRecyclerAdapter() }

    override fun init() {
        setUpRecyclers()
        observers()
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeState.collect {
                    handleHomeState(it)
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
}