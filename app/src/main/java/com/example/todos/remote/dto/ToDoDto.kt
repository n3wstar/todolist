package com.example.todos.remote.dto

data class TodoDto(
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long?,
    val done: Boolean,
    val color: String?, // "#FFFFFF"
    val created_at: Long,
    val changed_at: Long,
    val last_updated_by: String
)