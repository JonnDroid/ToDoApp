package com.example.composeactivity2.ui.presentation.todolist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composeactivity2.data.model.ToDoEvent
import com.example.composeactivity2.ui.composable.AppBar
import com.example.composeactivity2.ui.composable.FloatingButton
import com.example.composeactivity2.ui.theme.IndianRed

private lateinit var stateValues: List<Any>

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ToDoListScreen(
    navController: NavController,
    toDoListViewModel: ToDoListViewModel,
    state: ToDoState
) {

    Scaffold(
        topBar = { AppBar("To Do List") },
        floatingActionButton = {
            FloatingButton {
                navController.navigate("input")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
                    items(state.todo.size) { index ->
                        ToDoListCard(toDoListViewModel, state, index)
                    }
                }
            }
        }
    }
}

@Composable
fun ToDoListCard(toDoListViewModel: ToDoListViewModel, state: ToDoState, index: Int) {
    var checkedState = state.todo[index].checkboxState != 0
    stateValues = listOf(state.todo[index].id, state.todo[index].text, checkedState)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                    updateStateValues(state, index, checkedState)
                    toDoListViewModel.onEvent(
                        ToDoEvent.UpsertItem(
                            state.todo[index].text,
                            state.todo[index].checkboxState
                        ), stateValues
                    )
                })
            Text(
                text = state.todo[index].text,
                modifier = Modifier.weight(1f),
                color = if (checkedState) Color.DarkGray else Color.Black,
                fontStyle = if (checkedState) FontStyle.Italic else FontStyle.Normal
            )
            IconButton(
                onClick = {
                    toDoListViewModel.onEvent(ToDoEvent.DeleteItem(state.todo[index]), stateValues)
                    updateStateValues(state, index, checkedState)
                },
                colors = IconButtonDefaults.iconButtonColors(IndianRed)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "delete icon",
                    modifier = Modifier.size(60.dp),
                    tint = Color.White
                )
            }
        }
    }
}

fun updateStateValues(state: ToDoState, index: Int, checkedState: Boolean) {
    stateValues = listOf(state.todo[index].id, state.todo[index].text, checkedState)
}
