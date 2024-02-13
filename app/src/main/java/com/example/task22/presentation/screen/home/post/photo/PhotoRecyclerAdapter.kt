package com.example.task22.presentation.screen.home.post.photo

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task22.databinding.ItemPhotoBinding
import com.example.task22.presentation.extension.loadImagesWithGlide

class PhotoRecyclerAdapter() : RecyclerView.Adapter<PhotoRecyclerAdapter.PhotoViewHolder>() {
    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.ivPhoto.loadImagesWithGlide(url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = minOf(3)

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}