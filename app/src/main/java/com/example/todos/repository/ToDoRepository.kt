package com.example.todos.repository

import com.example.todos.data.ToDoDao
import com.example.todos.data.TodoMapper
import com.example.todos.model.ToDoItem
import com.example.todos.remote.TodoRemoteDataSource
import com.example.todos.storage.FileStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TodoRepository(
    private val dao: ToDoDao,
    private val remote: TodoRemoteDataSource
) {

    val todos: Flow<List<ToDoItem>> =
        dao.observeAll()
            .map { list ->
                list.map { TodoMapper.entityToDomain(it) }
            }

    suspend fun refresh() {
        try {
            val serverItems = remote.load()

            dao.clear()
            dao.insertAll(
                serverItems.map { TodoMapper.domainToEntity(it) }
            )

        } catch (e: Exception) {

        }
    }

    suspend fun add(item: ToDoItem) {
        dao.insert(TodoMapper.domainToEntity(item))
        sync()
    }

    suspend fun update(item: ToDoItem) {
        dao.insert(TodoMapper.domainToEntity(item))
        sync()
    }

    suspend fun delete(uid: String) {
        dao.deleteById(uid)
        sync()
    }

    suspend fun getById(uid: String): ToDoItem? {
        return dao.getById(uid)?.let {
            TodoMapper.entityToDomain(it)
        }
    }

    private suspend fun sync() {
        try {
            val localItems = dao.observeAll()
                .first()
                .map { TodoMapper.entityToDomain(it) }

            val serverItems = remote.sync(localItems)

            dao.clear()
            dao.insertAll(
                serverItems.map { TodoMapper.domainToEntity(it) }
            )

        } catch (e: Exception) {

        }
    }
}


