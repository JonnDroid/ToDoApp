package com.example.composeactivity2.ui.presentation.todolist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.composeactivity2.domain.ToDo

data class ToDoState(
    val todo: List<ToDo> = emptyList(),
    val id: MutableState<Int> = mutableIntStateOf(0),
    val text: MutableState<String> = mutableStateOf(""),
    val checkboxState: MutableState<Int> = mutableIntStateOf(0)
)