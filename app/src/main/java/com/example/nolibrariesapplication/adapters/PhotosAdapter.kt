package com.example.nolibrariesapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nolibrariesapplication.R
import com.example.nolibrariesapplication.models.Photo
import com.example.nolibrariesapplication.remote.LoadPhotosAsyncTask
import com.example.nolibrariesapplication.util.Cache
import java.lang.Exception


class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val photo = differ.currentList[position]

        holder.itemView.apply {
            val image = findViewById<ImageView>(R.id.ivPhoto)
            val title = findViewById<TextView>(R.id.tvTitle)
            val loadPhotosAsyncTask = LoadPhotosAsyncTask(image, photo.photoId, photo.url)
            if (Cache.containsImage(photo.photoId)) {
                try {
                    image.setImageBitmap(Cache.getImage(photo.photoId))
                } catch (e: Exception) {
                    loadPhotosAsyncTask.execute()
                }
            } else {
                loadPhotosAsyncTask.execute()
            }
            title.text = photo.title
        }
    }


    inner class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.photoId == newItem.photoId
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}

