package com.example.task22.presentation.screen.home

import com.example.task22.data.remote.utils.Resource
import com.example.task22.domain.remote.model.GetOwner
import com.example.task22.domain.remote.model.GetPost
import com.example.task22.domain.remote.model.GetStory
import com.example.task22.domain.remote.use_case.GetPostsUseCase
import com.example.task22.domain.remote.use_case.GetStoriesUseCase
import com.example.task22.presentation.event.home.HomeFragmentEvents
import com.example.task22.presentation.mapper.toPresentation
import com.example.task22.presentation.state.home.HomeState
import junit.framework.TestCase.assertFalse
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
class HomeFragmentViewModelTest {
    @Mock
    private lateinit var getPostsUseCase: GetPostsUseCase

    @Mock
    private lateinit var getStoriesUseCase: GetStoriesUseCase

    private lateinit var viewModel: HomeFragmentViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = HomeFragmentViewModel(getPostsUseCase, getStoriesUseCase)

    }

    @Test
    fun `check if posts match returns true`() = runTest {
        val posts = listOf(
            GetPost(
                id = 1,
                title = "asdasdasd",
                comments = 2,
                images = listOf("asdasd", "asdasd"),
                likes = 4,
                getOwner = GetOwner(
                    "jemala",
                    "kakauridze",
                    postDate = 1231231312,
                    profile = "asdasdasd"
                ),
                shareContent = "share content"
            ),
            GetPost(
                id = 2,
                title = "asdasdasdasdasasd",
                comments = 5,
                images = listOf("asdasdasasdasd", "asdasd"),
                likes = 444,
                getOwner = GetOwner(
                    "jemal",
                    "kakauridze",
                    postDate = 1231451312,
                    profile = "asdasasddasd"
                ),
                shareContent = "share content"
            ),
            GetPost(
                id = 3,
                title = "asdasdasd",
                comments = 6,
                images = listOf("asdasd", "asdasdasdsadasdasd"),
                likes = 999,
                getOwner = GetOwner(
                    "jemali",
                    "kakauridze",
                    postDate = 1233431312,
                    profile = "asdaasdasdasd"
                ),
                shareContent = "share content"
            )
        )
        val homeState = mutableListOf<HomeState>()
        val job = launch { viewModel.homeState.toList(homeState) }

        whenever(getPostsUseCase()).thenReturn(flowOf(Resource.Success(posts)))

        viewModel.onEvent(HomeFragmentEvents.FetchPosts)
        delay(500)
        job.cancel()
        assertTrue(
            "Check if list of posts matches the list of HomeState's posts",
            homeState.any { state ->
                state.posts == posts.map { it.toPresentation() }
            })
        assertFalse("isLoading = false", homeState.last().isLoading)
    }

    @Test
    fun `check if stories match returns true`() = runTest {
        val stories = mutableListOf(
            GetStory("mamakacuri cover", 1, "nadiri"),
            GetStory("mamakacuri cover", 1, "lomi"),
            GetStory("mamakacuri cover", 1, "nadiri * 2"),
            GetStory("mamakacuri cover", 1, "lomi^2")
        )
        val homeState = mutableListOf<HomeState>()
        val job = launch { viewModel.homeState.toList(homeState) }
        whenever(getStoriesUseCase()).thenReturn(flowOf(Resource.Success(stories)))

        viewModel.onEvent(HomeFragmentEvents.FetchStories)

        delay(500)
        job.cancel()

        assertTrue(
            "Check if list of stories matches the list of HomeState's stories",
            homeState.any { state ->
                state.stories == stories.map { it.toPresentation() }
            })
        assertFalse("isLoading = false", homeState.last().isLoading)
    }

    @Test
    fun `fetch stories returns Error`() = runTest {
        val error = "error"
        val homeState = mutableListOf<HomeState>()
        val job = launch { viewModel.homeState.toList(homeState) }
        whenever(getStoriesUseCase()).thenReturn(flowOf(Resource.Error(error)))

        viewModel.onEvent(HomeFragmentEvents.FetchStories)

        delay(500)
        job.cancel()

        assertTrue("error message", homeState.any { it.errorMessage == error })
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}