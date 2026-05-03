package com.example.todos.remote

import com.example.todos.model.ToDoItem
import com.example.todos.remote.api.TodoApi
import com.example.todos.remote.dto.TodoListRequest
import com.example.todos.remote.mapper.toDomain
import com.example.todos.remote.mapper.toDto

class TodoRemoteDataSource(
    private val api: TodoApi
) {

    var revision: Int = 0

    suspend fun load(): List<ToDoItem> {
        val res = api.getList()
        revision = res.revision
        return res.list.map { it.toDomain() }
    }

    suspend fun sync(items: List<ToDoItem>): List<ToDoItem> {
        val res = api.updateList(
            revision,
            TodoListRequest(
                list = items.map { it.toDto() }
            )
        )

        revision = res.revision
        return res.list.map { it.toDomain() }
    }
}