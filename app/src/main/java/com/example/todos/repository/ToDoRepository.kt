package com.example.todos.repository

import com.example.todos.TodoRemoteDataSource
import com.example.todos.model.ToDoItem
import com.example.todos.storage.FileStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodoRepository(
    private val storage: FileStorage,
    private val remote: TodoRemoteDataSource
) {

    private val _todos = MutableStateFlow<List<ToDoItem>>(emptyList())
    val todos: StateFlow<List<ToDoItem>> = _todos

    init {
        load()
    }

    fun load() {
        storage.loadFromFile()
        
        val local = storage.getAll()

        // имитация запроса на сервер
        remote.loadTodos()

        _todos.value = local
    }

    fun add(item: ToDoItem) {
        storage.add(item)
        remote.sendTodo(item)
        _todos.value = storage.getAll()
    }

    fun update(item: ToDoItem) {
        storage.remove(item.uid)
        storage.add(item)

        remote.sendTodo(item)

        _todos.value = storage.getAll()
    }

    fun delete(uid: String) {
        storage.remove(uid)
        remote.deleteTodo(uid)

        _todos.value = storage.getAll()
    }

    fun getById(uid: String): ToDoItem? {
        return storage.getById(uid)
    }
}