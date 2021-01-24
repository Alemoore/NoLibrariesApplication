package com.example.nolibrariesapplication.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nolibrariesapplication.util.CustomClickListener
import com.example.nolibrariesapplication.R
import com.example.nolibrariesapplication.presenters.UsersPresenter
import com.example.nolibrariesapplication.adapters.UsersAdapter
import com.example.nolibrariesapplication.models.User
import com.example.nolibrariesapplication.util.Constants.userIdTag

class UsersActivity : AppCompatActivity(), CustomClickListener {
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var presenter: UsersPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        presenter = UsersPresenter()
        presenter.attachView(this)
        setupRecyclerView()
        presenter.loadUsers()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    fun refreshUserList(users: List<User>) {
        usersAdapter.differ.submitList(users)
    }

    private fun setupRecyclerView() {
        usersAdapter = UsersAdapter()
        usersAdapter.setOnItemClickListener(this)
        val recyclerView: RecyclerView = findViewById(R.id.rvUsers)
        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(this@UsersActivity)
        }
    }

    override fun onRecyclerViewClick(userId: Int) {
        //Toast.makeText(this, "It works", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, PhotosActivity::class.java)
        intent.putExtra(userIdTag, userId)
        startActivity(intent)
    }
}