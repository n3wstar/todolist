package com.example.todos.repository

import com.example.todos.model.ToDoItem
import com.example.todos.storage.FileStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoRepository(
    private val storage: FileStorage,
    private val remote: com.example.todos.remote.TodoRemoteDataSource
) {

    private val _todos = MutableStateFlow<List<ToDoItem>>(emptyList())
    val todos: StateFlow<List<ToDoItem>> = _todos

    init {
        load()
    }

    fun load() {
        CoroutineScope(Dispatchers.IO).launch {
            val local = storage.getAll()

            try {
                val server = remote.load()
                storage.replaceAll(server)
                _todos.value = server
            } catch (e: Exception) {
                _todos.value = local
            }
        }
    }

    fun add(item: ToDoItem) {
        CoroutineScope(Dispatchers.IO).launch {
            storage.add(item)

            val updated = remote.sync(storage.getAll())
            storage.replaceAll(updated)
            _todos.value = updated
        }
    }

    fun update(item: ToDoItem) {
        CoroutineScope(Dispatchers.IO).launch {
            storage.remove(item.uid)
            storage.add(item)

            val updated = remote.sync(storage.getAll())
            storage.replaceAll(updated)
            _todos.value = updated
        }
    }

    fun delete(uid: String) {
        CoroutineScope(Dispatchers.IO).launch {
            storage.remove(uid)

            val updated = remote.sync(storage.getAll())
            storage.replaceAll(updated)
            _todos.value = updated
        }
    }

    fun getById(uid: String): ToDoItem? {
        return storage.getById(uid)
    }
}


