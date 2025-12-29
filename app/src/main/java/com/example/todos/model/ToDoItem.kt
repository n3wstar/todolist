package com.example.todos.model


import android.graphics.Color
import java.util.UUID

enum class Importance{
    LOW,
    NORMAL,
    HIGH
}

fun importanceFromJson(name: String?): Importance {
    return when (name) {
        "низкая" -> Importance.LOW
        "высокая" -> Importance.HIGH
        else -> Importance.NORMAL
    }
}

data class ToDoItem(
    val uid : String = UUID.randomUUID().toString(),
    val text : String,
    val importance: Importance = Importance.NORMAL,
    val color: Int = Color.WHITE,
    val deadline: Long? = null,
    val isDone: Boolean = false
)
