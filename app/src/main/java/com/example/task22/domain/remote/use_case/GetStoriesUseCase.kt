package com.example.task22.domain.remote.use_case

import com.example.task22.domain.remote.repository.StoriesRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(private val storiesRepository: StoriesRepository) {
    suspend operator fun invoke() =
        storiesRepository.getStories()
}