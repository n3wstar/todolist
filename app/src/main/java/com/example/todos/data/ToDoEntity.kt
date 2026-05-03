package com.example.todos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDoEntity(
    @PrimaryKey val uid: String,
    val text: String,
    val importance: String,
    val color: Int,
    val deadline: Long?,
    val isDone: Boolean
)
