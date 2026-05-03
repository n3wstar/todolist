package com.example.todos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.todos.data.DatabaseProvider
import com.example.todos.model.ToDoItem
import com.example.todos.network.NetworkToDo
import com.example.todos.pages.ToDoEditScreen
import com.example.todos.remote.TodoRemoteDataSource
import com.example.todos.repository.TodoRepository
import com.example.todos.storage.FileStorage
import com.example.todos.ui.theme.ToDosTheme
import com.example.todos.viewModel.TodoViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DatabaseProvider.create(this)

        val api = NetworkToDo.api
        val remote = TodoRemoteDataSource(api)
        val repository = TodoRepository(db.todoDao(), remote)

        val viewModel = TodoViewModel(repository)

        setContent {
            NavGraph(viewModel)
        }
    }
}
