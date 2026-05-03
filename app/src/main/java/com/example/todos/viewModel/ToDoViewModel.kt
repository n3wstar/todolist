package com.example.todos.viewModel

import androidx.lifecycle.ViewModel
import com.example.todos.model.ToDoItem
import com.example.todos.repository.TodoRepository

class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    val todos = repository.todos

    fun add(item: ToDoItem) = repository.add(item)
    fun update(item: ToDoItem) = repository.update(item)
    fun delete(uid: String) = repository.delete(uid)

    fun getById(uid: String?) = uid?.let { repository.getById(it) }
}