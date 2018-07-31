package com.example.jgodi.jpapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun insert(userEntity: UserEntity)

    @Insert(onConflict = REPLACE)
    fun insertAll(userEntity: List<UserEntity>)

    @Query("SELECT * FROM user_table")
    fun getListUsers(): Flowable<List<UserEntity>>

    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getSingleUser(userId: String): Flowable<UserEntity>
}