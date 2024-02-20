package com.example.task22.presentation.screen.details

import com.example.task22.data.remote.utils.Resource
import com.example.task22.domain.remote.model.GetOwner
import com.example.task22.domain.remote.model.GetPost
import com.example.task22.domain.remote.use_case.GetPostByIdUseCase
import com.example.task22.presentation.event.detail.DetailFragmentEvent
import com.example.task22.presentation.state.detail.DetailState
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetailFragmentViewModelTest {

    @Mock
    private lateinit var getPostByIdUseCase: GetPostByIdUseCase

    private lateinit var viewModel: DetailFragmentViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = DetailFragmentViewModel(getPostByIdUseCase)
    }

    @Test
    fun `fetch post by id returns Success`() = runTest {
        val post = GetPost(
            23,
            999,
            listOf("https://surati/jemali", "https://surati/kuntiani_jemali"),
            57,
            GetOwner(
                "Jemali",
                "Kakauridze",
                1234235221,
                "https://image/simpatiuriJemali"
            ),
            "share content",
            "mamakacuri title"
        )
        val detailState = mutableListOf<DetailState>()
        val job = launch { viewModel.detailState.toList(detailState) }
        whenever(getPostByIdUseCase(post.id)).thenReturn(flowOf(Resource.Success(post)))

        viewModel.onEvent(DetailFragmentEvent.FetchPost(post.id))
        delay(500)
        job.cancel()

        assertTrue("ragaca", detailState.any { state ->
            state.post?.id == post.id
        })
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}