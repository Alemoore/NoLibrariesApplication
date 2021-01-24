package com.example.nolibrariesapplication.presenters

import com.example.nolibrariesapplication.models.User
import com.example.nolibrariesapplication.remote.UsersAsyncTask
import com.example.nolibrariesapplication.ui.UsersActivity


class UsersPresenter {
    private var view: UsersActivity? = null
    private lateinit var usersAsyncTask: UsersAsyncTask

    fun attachView(view: UsersActivity) {
        this.view = view
    }

    fun detachView() {
        view = null
    }

    fun loadUsers(){
        usersAsyncTask = UsersAsyncTask(this)
        usersAsyncTask.execute()
    }

    fun refreshView(users: List<User>){
        view?.refreshUserList(users)
    }
}