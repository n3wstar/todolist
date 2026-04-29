package com.example.todos.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todos.TodoItemUI

@Composable
fun TodoListScreen(
    items: List<TodoItemUI>,
    onAddClick: () -> Unit,
    onItemClick: (TodoItemUI) -> Unit,
    onDelete: (String) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items) { item ->

                SwipeToDeleteItem(
                    item = item,
                    onDelete = { onDelete(item.uid) },
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteItem(
    item: TodoItemUI,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
            }
            true
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text("Delete")
            }
        },
        content = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = onClick
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(item.text)
                    Text(if (item.isDone) "✔ Выполнено" else "Не выполнено")
                }
            }
        }
    )
}