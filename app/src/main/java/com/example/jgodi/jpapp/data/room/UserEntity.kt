package com.example.jgodi.jpapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class UserEntity(@PrimaryKey(autoGenerate = false) val id: Int = 0,
                 @ColumnInfo(name = "first_name") val firstName: String,
                 @ColumnInfo(name = "last_name") val lastName: String,
                 @ColumnInfo(name = "avatar") val avatar: String)