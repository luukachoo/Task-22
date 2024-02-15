package com.example.task22.presentation.screen.details

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.task22.R
import com.example.task22.databinding.FragmentDetailsBinding
import com.example.task22.presentation.event.detail.DetailFragmentEvent
import com.example.task22.presentation.extension.convertEpochDateToRegularDate
import com.example.task22.presentation.helper.Observer
import com.example.task22.presentation.screen.base.BaseFragment
import com.example.task22.presentation.state.detail.DetailState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate),
    Observer {

    private val viewModel: DetailFragmentViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun init() {
        val id = args.id
        viewModel.onEvent(DetailFragmentEvent.FetchPost(args.id))
        observers()
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
            }
        }
    }
}