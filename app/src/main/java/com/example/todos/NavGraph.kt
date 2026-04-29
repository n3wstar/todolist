package com.example.todos

import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.*
import com.example.todos.pages.*
import com.example.todos.viewModel.TodoViewModel


@Composable
fun NavGraph(viewModel: TodoViewModel) {

    val navController = rememberNavController()
    val todos by viewModel.todos.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {

        composable("list") {

            TodoListScreen(
                items = todos,
                onAddClick = {
                    navController.navigate("edit")
                },
                onItemClick = { item ->
                    navController.navigate("edit?uid=${item.uid}")
                },
                onDelete = { uid ->
                    viewModel.delete(uid)
                }
            )
        }

        composable("edit?uid={uid}") { backStack ->

            val uid = backStack.arguments?.getString("uid")

            val item = uid?.let { viewModel.getById(it) }

            ToDoEditScreenWrapper(
                item = item,
                onSave = { savedItem ->

                    if (item == null) {
                        viewModel.add(savedItem)
                    } else {
                        viewModel.update(savedItem)
                    }

                    navController.popBackStack()
                }
            )
        }
    }
}