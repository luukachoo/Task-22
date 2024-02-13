package com.example.task22.presentation.screen.home.story

import androidx.recyclerview.widget.DiffUtil
import com.example.task22.presentation.model.Story

class StoryDiffCallback : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem == newItem
}