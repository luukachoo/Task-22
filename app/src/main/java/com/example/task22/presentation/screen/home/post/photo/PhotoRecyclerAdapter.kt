package com.example.task22.presentation.screen.home.post.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task22.databinding.ItemPhotoBinding
import com.example.task22.presentation.extension.loadImagesWithGlide
import com.google.android.datatransport.runtime.logging.Logging.d
import kotlin.math.min

class PhotoCollageAdapter(private val photos: List<String>) :
    RecyclerView.Adapter<PhotoCollageAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            d("PhotoCollageAdapter", "Binding image: $url")
            binding.ivPhoto.loadImagesWithGlide(url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        d("PhotoCollageAdapter", "onCreateViewHolder called")
        return PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        d("PhotoCollageAdapter", "onBindViewHolder called for position: $position")
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        d("PhotoCollageAdapter", "getItemCount: ${min(photos.size, 3)}")
        return min(photos.size, 3)
    }
}
