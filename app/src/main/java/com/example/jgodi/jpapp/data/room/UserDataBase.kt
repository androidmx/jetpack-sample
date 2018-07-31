package com.example.jgodi.jpapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(UserEntity::class), version = 1)
abstract class UserDataBase
    : RoomDatabase() {

    abstract fun userDao(): UserDao

}