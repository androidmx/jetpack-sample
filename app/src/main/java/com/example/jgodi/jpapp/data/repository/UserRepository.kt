package com.example.jgodi.jpapp.data.repository

import androidx.lifecycle.LiveData
import com.example.jgodi.jpapp.domain.User
import io.reactivex.disposables.Disposable

interface UserRepository {
    fun getAllCompositeDisposables(): List<Disposable>
    fun getListUsers(page: Int, perPage: Int): LiveData<List<User>>
    fun getSingleUser(userId: Int): LiveData<User>
}