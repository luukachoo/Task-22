package com.example.task22.presentation.screen.home.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task22.databinding.ItemStoryRecyclerBinding
import com.example.task22.presentation.extension.loadImagesWithGlide
import com.example.task22.presentation.model.Story

class StoryRecyclerAdapter :
    ListAdapter<Story, StoryRecyclerAdapter.StoryViewHolder>(StoryDiffCallback()) {
    inner class StoryViewHolder(private val binding: ItemStoryRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) = with(binding) {
            tvTitle.text = story.title
            ivCover.loadImagesWithGlide(story.cover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StoryViewHolder(
        ItemStoryRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}