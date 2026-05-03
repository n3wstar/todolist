package com.example.todos.remote.dto

data class TodoListResponse(
    val status: String,
    val list: List<TodoDto>,
    val revision: Int
)