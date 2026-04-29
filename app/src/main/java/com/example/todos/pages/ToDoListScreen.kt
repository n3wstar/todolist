package com.example.todos.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todos.model.ToDoItem

@Composable
fun TodoListScreen(
    items: List<ToDoItem>,
    onAddClick: () -> Unit,
    onItemClick: (ToDoItem) -> Unit,
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

            items(items = items,
                key = {it.uid}
            ) { item ->

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
    item: ToDoItem,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                true
            }
        }
    )

    SwipeToDismissBox(
        state = state,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp),
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
                onClick = onClick,
                colors = CardDefaults.cardColors(
                    containerColor = Color(item.color)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        item.text,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Text(if (item.isDone) "✔ Выполнено" else "Не выполнено")
                }
            }
        }
    )
}