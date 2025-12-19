package com.example.todos.model

import android.graphics.Color
import org.json.JSONObject
import java.util.UUID


fun parseTodoItem(json: JSONObject): ToDoItem? {
    return try {
        val text = json.getString("text")

        val uid = json.optString("uid", UUID.randomUUID().toString())

        val importance = importanceFromJson(
            json.optString("importance", "")
        )

        val color = if (json.has("color")) {
            json.getInt("color")
        } else {
            Color.WHITE
        }

        val deadline = if (json.has("deadline")) {
            json.getLong("deadline")
        } else {
            null
        }

        val isDone = json.optBoolean("isDone", false)

        ToDoItem(
            uid = uid,
            text = text,
            importance = importance,
            color = color,
            deadline = deadline,
            isDone = isDone
        )
    } catch (e: Exception) {
        null
    }
}

val ToDoItem.json: JSONObject
    get() {
        val json = JSONObject()

        json.put("uid", uid)
        json.put("text", text)
        json.put("isDone", isDone)

        if (importance != Importance.NORMAL) {
            json.put(
                "importance",
                when (importance) {
                    Importance.LOW -> "неважная"
                    Importance.HIGH -> "важная"
                    else -> "обычная"
                }
            )
        }

        if (color != Color.WHITE) {
            json.put("color", color)
        }


        if (deadline != null) {
            json.put("deadline", deadline)
        }

        return json
    }