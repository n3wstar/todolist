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
import com.example.todos.model.ToDoItem
import com.example.todos.pages.ToDoEditScreen
import com.example.todos.storage.FileStorage
import com.example.todos.ui.theme.ToDosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val storage = FileStorage(this)
        storage.loadFromFile()

        val item = ToDoItem(text = "накормить кота")
        storage.add(item)

        storage.remove(item.uid)

        setContent {
            ToDosTheme {
                ToDoEditScreen()
            }
        }
    }
}
