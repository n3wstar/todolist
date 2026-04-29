package com.example.todos

import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.todos.pages.*


data class TodoItemUI(
    val uid: String,
    val text: String,
    val isDone: Boolean
)
@Composable
fun NavGraph() {

    val navController = rememberNavController()

    var items by remember { mutableStateOf(listOf<TodoItemUI>()) }

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            TodoListScreen(
                items = items,
                onAddClick = {
                    navController.navigate("edit")
                },
                onItemClick = { item ->
                    navController.navigate("edit?uid=${item.uid}")
                },
                onDelete = { uid ->
                    items = items.filter { it.uid != uid }
                }
            )
        }


        composable("edit?uid={uid}") { backStackEntry ->

            val uid = backStackEntry.arguments?.getString("uid")
            val existingItem = items.find { it.uid == uid }

            ToDoEditScreenWrapper(
                item = existingItem,
                onSave = { newItem ->

                    items = if (existingItem == null) {
                        items + newItem
                    } else {
                        items.map {
                            if (it.uid == newItem.uid) newItem else it
                        }
                    }

                    navController.popBackStack()
                }
            )
        }
    }
}