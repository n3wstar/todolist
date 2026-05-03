package com.example.todos.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ToDoEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): ToDoDao
}