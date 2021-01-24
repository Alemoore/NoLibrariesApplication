package com.example.nolibrariesapplication.presenters

import android.util.Log
import android.widget.Toast
import com.example.nolibrariesapplication.models.Album
import com.example.nolibrariesapplication.models.Photo
import com.example.nolibrariesapplication.remote.AlbumsAsyncTask
import com.example.nolibrariesapplication.remote.PhotosAsyncTask
import com.example.nolibrariesapplication.ui.PhotosActivity


class PhotosPresenter {
    private var view: PhotosActivity? = null
    private lateinit var albumsAsyncTask: AlbumsAsyncTask
    private lateinit var photosAsyncTask: PhotosAsyncTask

    fun attachView(view: PhotosActivity) {
        this.view = view
    }

    fun detachView() {
        view = null
    }

    fun startLoadPhotos(userId: Int) {
        albumsAsyncTask = AlbumsAsyncTask(userId, this)
        albumsAsyncTask.execute()
    }

    fun getPhotosFromAlbums(albums: List<Album>?) {
        if (albums != null) {
            photosAsyncTask = PhotosAsyncTask(this, albums.map { it.albumId })
            photosAsyncTask.execute()
        } else {
            Toast.makeText(view, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshView(photos: List<Photo>?) {
        if (photos != null) {
            view?.refreshPhotosList(photos)
        } else {
            Toast.makeText(view, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}