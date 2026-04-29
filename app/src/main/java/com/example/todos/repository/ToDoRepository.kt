package com.example.todos.repository

import com.example.todos.model.ToDoItem
import com.example.todos.storage.FileStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodoRepository(
    private val storage: FileStorage
) {

    private val _todos = MutableStateFlow<List<ToDoItem>>(emptyList())
    val todos: StateFlow<List<ToDoItem>> = _todos

    init {
        load()
    }

    private fun load() {
        storage.loadFromFile()
        _todos.value = storage.getAll()
    }

    fun add(item: ToDoItem) {
        storage.add(item)
        _todos.value = storage.getAll()
    }

    fun update(item: ToDoItem) {
        storage.remove(item.uid)
        storage.add(item)
        _todos.value = storage.getAll()
    }

    fun delete(uid: String) {
        storage.remove(uid)
        _todos.value = storage.getAll()
    }

    fun getById(uid: String): ToDoItem? {
        return storage.getById(uid)
    }
}