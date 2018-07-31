package com.example.jgodi.jpapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jgodi.jpapp.data.remote.UserModel
import com.example.jgodi.jpapp.data.repository.UserRepositoryImp

class UserProfileViewModel : ViewModel() {
    private val userRepository = UserRepositoryImp()
    private var _Single_user: LiveData<UserModel.SingleUser>? = null
    val singleUser : LiveData<UserModel.SingleUser>?
        get() = _Single_user

    fun init(userId:Int) {
        if(this.singleUser != null) {
            return
        }

        this._Single_user = userRepository.getUser(userId)
    }
}
