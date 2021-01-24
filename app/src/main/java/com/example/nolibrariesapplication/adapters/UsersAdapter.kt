package com.example.nolibrariesapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nolibrariesapplication.util.CustomClickListener
import com.example.nolibrariesapplication.R
import com.example.nolibrariesapplication.models.User

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private var customClickListener: CustomClickListener? = null

    fun setOnItemClickListener(listener: CustomClickListener) {
        customClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.user_preview, parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.itemView.apply {
            val userName = findViewById<TextView>(R.id.tvUserName)
            userName.text = user.name
            setOnClickListener {
                customClickListener?.onRecyclerViewClick(user.id)
            }
        }
    }

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    private val differCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}