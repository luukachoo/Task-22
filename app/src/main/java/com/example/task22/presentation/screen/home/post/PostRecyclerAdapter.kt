package com.example.task22.presentation.screen.home.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task22.R
import com.example.task22.databinding.ItemPostRecyclerBinding
import com.example.task22.presentation.extension.convertEpochDateToRegularDate
import com.example.task22.presentation.extension.loadImagesWithGlide
import com.example.task22.presentation.model.Post

class PostRecyclerAdapter :
    ListAdapter<Post, PostRecyclerAdapter.PostViewHolder>(PostDiffCallback()) {

    private var onClick: ((Post) -> Unit)? = null

    inner class PostViewHolder(private val binding: ItemPostRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = with(binding) {
            val fullName = itemView.context.getString(
                R.string.full_name_format,
                post.owner.firstName,
                post.owner.lastName
            )
            tvName.text = fullName
            ivAvatar.loadImagesWithGlide(post.owner.profile)
            tvDate.convertEpochDateToRegularDate(post.owner.postDate.toLong())
            tvShare.text = post.shareContent
            tvLikes.text = itemView.context.getString(R.string.likes, post.likes)
            tvComments.text = itemView.context.getString(R.string.comments, post.comments)
            tvDescription.text = post.title

            post.images?.let { images ->
                if (images.isNotEmpty()) {
                    ivImageFirst.loadImagesWithGlide(images[0])
                }
                if (images.size > 1) {
                    ivImageSecond.loadImagesWithGlide(images[1])
                }
                if (images.size > 2) {
                    ivImageThird.loadImagesWithGlide(images[2])
                }
            }

            root.setOnClickListener { onClick?.invoke(post) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(
        ItemPostRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun onClick(click: (Post) -> Unit) {
        this.onClick = click
    }
}