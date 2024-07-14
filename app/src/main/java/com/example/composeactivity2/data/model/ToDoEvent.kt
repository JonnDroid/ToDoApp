package com.example.composeactivity2.data.model

import com.example.composeactivity2.domain.ToDo

sealed interface ToDoEvent {
    data class DeleteItem(val todo: ToDo) : ToDoEvent
    data class UpsertItem(val text: String, val checkboxState: Int) : ToDoEvent
}