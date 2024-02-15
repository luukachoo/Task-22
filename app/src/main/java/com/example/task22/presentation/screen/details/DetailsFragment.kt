package com.example.task22.presentation.screen.details

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.task22.R
import com.example.task22.databinding.FragmentDetailsBinding
import com.example.task22.presentation.event.detail.DetailFragmentEvent
import com.example.task22.presentation.event.home.HomeFragmentEvents
import com.example.task22.presentation.extension.convertEpochDateToRegularDate
import com.example.task22.presentation.extension.loadImagesWithGlide
import com.example.task22.presentation.extension.showSnackbar
import com.example.task22.presentation.helper.Listener
import com.example.task22.presentation.helper.Observer
import com.example.task22.presentation.screen.base.BaseFragment
import com.example.task22.presentation.state.detail.DetailState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate),
    Observer, Listener {

    private val viewModel: DetailFragmentViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun init() {
        viewModel.onEvent(DetailFragmentEvent.FetchPost(args.id))
        observers()
        listeners()
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailState.collect {
                    handleDetailFragmentState(it)
                }
            }
        }
    }

    private fun handleDetailFragmentState(state: DetailState) {
        binding.progressBar.isVisible = state.isLoading

        state.post?.let {
            binding.apply {
                tvName.text = getString(
                    R.string.full_name_format,
                    state.post.owner.firstName,
                    state.post.owner.lastName
                )
                tvDate.convertEpochDateToRegularDate(state.post.owner.postDate.toLong())
                tvDescription.text = state.post.title
                tvComments.text = state.post.comments.toString()
                tvLikes.text = state.post.likes.toString()
                tvShare.text = state.post.shareContent
                ivAvatar.loadImagesWithGlide(state.post.owner.profile)
                state.post.images?.get(0)?.let { it1 -> ivImageFirst.loadImagesWithGlide(it1) }
            }
        }

        state.errorMessage?.let {
            binding.root.showSnackbar(message = it)
            viewModel.onEvent(DetailFragmentEvent.ResetErrorMessage)
        }
    }

    override fun listeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}