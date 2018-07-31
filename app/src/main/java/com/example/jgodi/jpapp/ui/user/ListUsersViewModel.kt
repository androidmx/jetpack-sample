package com.example.jgodi.jpapp.ui.user

import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import com.example.jgodi.jpapp.data.repository.UserRepository
import com.example.jgodi.jpapp.data.repository.UserRepositoryImp
import com.example.jgodi.jpapp.domain.User
import io.reactivex.disposables.CompositeDisposable

class ListUsersViewModel : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()
    private var listUsers: LiveData<List<User>>? = null

    private var userRepository: UserRepository = UserRepositoryImp()

    fun getListUsers(page: Int, perPage: Int): LiveData<List<User>>? {
        listUsers = MutableLiveData<List<User>>()
        listUsers = userRepository.getListUsers(page, perPage)
        return listUsers
    }

    @OnLifecycleEvent(ON_DESTROY)
    private fun unSubscribeViewModel() {
        userRepository.getAllCompositeDisposables().forEach {
            compositeDisposable.addAll(it)
        }
        compositeDisposable.clear()
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }
}
