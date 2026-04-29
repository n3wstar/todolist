package com.example.todos

import com.example.todos.model.ToDoItem

class TodoRemoteDataSource {

    fun loadTodos(): List<ToDoItem> {
        android.util.Log.d("API", "LOAD todos from backend")
        return emptyList() // заглушка
    }

    fun sendTodo(item: ToDoItem) {
        android.util.Log.d("API", "SEND todo to backend: $item")
    }

    fun deleteTodo(uid: String) {
        android.util.Log.d("API", "DELETE todo from backend: $uid")
    }
}