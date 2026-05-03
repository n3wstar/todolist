package com.example.todos.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    fun create(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "todo-db"
        ).build()
    }
}