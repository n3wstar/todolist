package com.example.todos.data

import com.example.todos.model.Importance
import com.example.todos.model.ToDoItem

object TodoMapper {

    fun entityToDomain(entity: ToDoEntity): ToDoItem =
        ToDoItem(
            uid = entity.uid,
            text = entity.text,
            importance = importanceFromString(entity.importance),
            color = entity.color,
            deadline = entity.deadline,
            isDone = entity.isDone
        )

    fun domainToEntity(domain: ToDoItem): ToDoEntity =
        ToDoEntity(
            uid = domain.uid,
            text = domain.text,
            importance = importanceToString(domain.importance),
            color = domain.color,
            deadline = domain.deadline,
            isDone = domain.isDone
        )

    private fun importanceFromString(value: String): Importance {
        return when (value.lowercase()) {
            "low" -> Importance.LOW
            "basic" -> Importance.NORMAL
            "important" -> Importance.HIGH
            else -> Importance.NORMAL
        }
    }

    private fun importanceToString(value: Importance): String {
        return when (value) {
            Importance.LOW -> "low"
            Importance.NORMAL -> "basic"
            Importance.HIGH -> "important"
        }
    }
}