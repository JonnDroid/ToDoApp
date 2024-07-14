package com.example.composeactivity2.ui.presentation.mainscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.composeactivity2.data.database.ToDoDatabase
import com.example.composeactivity2.ui.presentation.input.InputScreen
import com.example.composeactivity2.ui.presentation.input.InputViewModel
import com.example.composeactivity2.ui.presentation.input.InputViewModelFactory
import com.example.composeactivity2.ui.presentation.todolist.ToDoListScreen
import com.example.composeactivity2.ui.presentation.todolist.ToDoListViewModel
import com.example.composeactivity2.ui.presentation.todolist.ToDoListViewModelFactory
import com.example.composeactivity2.ui.theme.ComposeActivity2Theme

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            "todo.db"
        ).build()
    }

    private val inputViewModelFactory: InputViewModelFactory by lazy {
        InputViewModelFactory(database.dao)
    }

    private val inputViewModel: InputViewModel by lazy {
        ViewModelProvider(this, inputViewModelFactory)[InputViewModel::class.java]
    }

    private val toDoListViewModelFactory: ToDoListViewModelFactory by lazy {
        ToDoListViewModelFactory(database.dao)
    }

    private val toDoListViewModel: ToDoListViewModel by lazy {
        ViewModelProvider(this, toDoListViewModelFactory)[ToDoListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeActivity2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreen(inputViewModel, toDoListViewModel)
                }
            }
        }
    }
}

@Composable
fun AppScreen(inputViewModel: InputViewModel, toDoListViewModel: ToDoListViewModel) {
    val navController = rememberNavController()
    val state by toDoListViewModel.state.collectAsState()

    NavHost(navController = navController, startDestination = "todolist", builder = {
        composable("todolist") {
            ToDoListScreen(navController, toDoListViewModel, state)
        }
        composable("input") {
            InputScreen(inputViewModel) { navController.navigate("todolist") }
        }
    })
}
