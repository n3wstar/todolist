package com.example.todos.remote.mapper

import com.example.todos.model.Importance
import com.example.todos.model.ToDoItem
import com.example.todos.remote.dto.TodoDto

fun ToDoItem.toDto(): TodoDto {
    return TodoDto(
        id = uid,
        text = text,
        importance = when (importance) {
            Importance.LOW -> "low"
            Importance.NORMAL -> "basic"
            Importance.HIGH -> "important"
        },
        deadline = deadline,
        done = isDone,
        color = "#%06X".format(0xFFFFFF and color),
        created_at = System.currentTimeMillis(),
        changed_at = System.currentTimeMillis(),
        last_updated_by = "android"
    )
}

fun TodoDto.toDomain(): ToDoItem {
    return ToDoItem(
        uid = id,
        text = text,
        importance = when (importance) {
            "low" -> Importance.LOW
            "important" -> Importance.HIGH
            else -> Importance.NORMAL
        },
        deadline = deadline,
        isDone = done,
        color = color?.let {
            android.graphics.Color.parseColor(it)
        } ?: android.graphics.Color.WHITE
    )
}