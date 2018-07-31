package com.example.jgodi.jpapp.data.remote

import com.example.jgodi.jpapp.BuildConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {
    @GET("/api/users/{userId}")
    fun getUser(@Path("userId") userId:Int):Call<UserModel.SingleUser>

    @GET("/api/users/")
    fun getListUsers(@Query("page") page: Int, @Query("per_page") perPage: Int): Single<UserModel.ListUsers>

    @GET("/api/users/{userId}")
    fun getSingleUser(@Path("userId") userId: Int): Single<UserModel.SingleUser>

    companion object {
        fun create() : RestService {
//            val loggingInterceptor = HttpLoggingInterceptor()
//                    .apply {
//                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
//                        else HttpLoggingInterceptor.Level.NONE
//                    }

            val loggingInterceptor = LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .build()

            val client = OkHttpClient().newBuilder()
                    .addInterceptor(loggingInterceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://reqres.in/")
                    .build()

            return retrofit.create(RestService::class.java)
        }
    }
}