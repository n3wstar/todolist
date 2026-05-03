package com.example.todos.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.model.ToDoItem
import com.example.todos.repository.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    val todos = repository.todos.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    fun add(item: ToDoItem) {
        viewModelScope.launch {
            repository.add(item)
        }
    }

    fun update(item: ToDoItem) {
        viewModelScope.launch {
            repository.update(item)
        }
    }

    fun delete(uid: String) {
        viewModelScope.launch {
            repository.delete(uid)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            repository.refresh()
        }
    }
}