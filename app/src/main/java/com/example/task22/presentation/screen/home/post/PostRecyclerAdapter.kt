package com.example.task22.presentation.screen.home.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task22.R
import com.example.task22.databinding.ItemPostRecyclerBinding
import com.example.task22.presentation.collage_layout_manager.PhotoCollageLayoutManager
import com.example.task22.presentation.extension.convertEpochDateToRegularDate
import com.example.task22.presentation.extension.loadImagesWithGlide
import com.example.task22.presentation.model.Post
import com.example.task22.presentation.screen.home.post.photo.PhotoCollageAdapter

class PostRecyclerAdapter :
    ListAdapter<Post, PostRecyclerAdapter.PostViewHolder>(PostDiffCallback()) {

    private var onClick: ((Post) -> Unit)? = null

    inner class PostViewHolder(private val binding: ItemPostRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageContainer.layoutManager = PhotoCollageLayoutManager()
        }

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

            imageContainer.adapter = post.images?.let { PhotoCollageAdapter(it) }

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
        val post = getItem(position)
        holder.bind(post)
    }

    fun onClick(click: (Post) -> Unit) {
        this.onClick = click
    }
}