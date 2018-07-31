package com.example.jgodi.jpapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jgodi.jpapp.data.remote.UserModel
import com.example.jgodi.jpapp.data.remote.RestService
import com.example.jgodi.jpapp.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.jgodi.jpapp.domain.User


class UserRepositoryImp
    : UserRepository {

    private val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    private val restService: RestService = RestService.create()

    override fun getAllCompositeDisposables(): List<Disposable> {
        return allCompositeDisposable
    }

    override fun getListUsers(page: Int, perPage: Int): LiveData<List<User>> {
        val mutableLiveData = MutableLiveData<List<User>>()
        val disposable = restService.getListUsers(page, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listUsers: UserModel.ListUsers ->
                    mutableLiveData.value = collectionTransform(listUsers)
                }, { throwable: Throwable? -> throwable?.printStackTrace() })

        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    override fun getSingleUser(userId: Int): LiveData<User> {
        val mutableLiveData = MutableLiveData<User>()
        val disposable = restService.getSingleUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ single: UserModel.SingleUser ->
                    mutableLiveData.value = transform(single.data)
                }, { throwable: Throwable? -> throwable?.printStackTrace() })

        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    private fun transform(data: UserModel.Data): User {
        return User(data.id,
                data.firstName + " " + data.lastName,
                data.avatar)
    }

    private fun collectionTransform(listUsers: UserModel.ListUsers): List<User> {
        val users = ArrayList<User>()
        listUsers.data.forEach {
            users.add(transform(it))
        }

        return users
    }


    fun getUser(userId:Int):LiveData<UserModel.SingleUser> {
        val data = MutableLiveData<UserModel.SingleUser>()

        restService.getUser(userId).enqueue(object: Callback<UserModel.SingleUser> {
            override fun onResponse(call: Call<UserModel.SingleUser>?, single: Response<UserModel.SingleUser>?) {
                if(single?.isSuccessful!!) {
                    data.value = single?.body()
                } else {
                    // error response, no access to resource?
                }
            }

            override fun onFailure(call: Call<UserModel.SingleUser>?, t: Throwable?) {

            }
        })

        return data
    }
}