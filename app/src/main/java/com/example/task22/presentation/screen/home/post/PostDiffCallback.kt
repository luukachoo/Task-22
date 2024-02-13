package com.example.task22.presentation.screen.home.post

import androidx.recyclerview.widget.DiffUtil
import com.example.task22.presentation.model.Post

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
}