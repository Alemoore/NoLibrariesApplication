package com.example.nolibrariesapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nolibrariesapplication.presenters.PhotosPresenter
import com.example.nolibrariesapplication.R
import com.example.nolibrariesapplication.adapters.PhotosAdapter
import com.example.nolibrariesapplication.models.Photo
import com.example.nolibrariesapplication.util.Constants.userIdTag

class PhotosActivity: AppCompatActivity() {
    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var presenter: PhotosPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        presenter = PhotosPresenter()
        presenter.attachView(this)
        setupRecyclerView()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val userId = intent.extras!!.getInt(userIdTag)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarPhotos)
        progressBar.visibility = View.VISIBLE
        presenter.startLoadPhotos(userId)
    }

    fun refreshPhotosList(photos: List<Photo>){
        val progressBar = findViewById<ProgressBar>(R.id.progressBarPhotos)
        progressBar.visibility = View.GONE
        photosAdapter.differ.submitList(photos)
    }

    private fun setupRecyclerView(){
        photosAdapter = PhotosAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.rvPhotos)
        recyclerView.apply {
            adapter = photosAdapter
            layoutManager = LinearLayoutManager(this@PhotosActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            startActivity(Intent(this, UsersActivity::class.java))
            return true
        }
        return false

    }

}