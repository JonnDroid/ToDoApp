package com.example.composeactivity2.ui.presentation.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeactivity2.data.dao.ToDoDao
import com.example.composeactivity2.data.model.ToDoEvent
import com.example.composeactivity2.domain.ToDo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ToDoListViewModel(private val dao: ToDoDao) : ViewModel() {

    private var _toDos =
        dao.getItemList().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ToDoState())
    val state =
        combine(_state, _toDos) { state, toDos ->
            state.copy(
                todo = toDos
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ToDoState())

    fun onEvent(event: ToDoEvent, stateValues: List<Any>) {
        when (event) {
            is ToDoEvent.DeleteItem -> {
                viewModelScope.launch {
                    dao.deleteItem(event.todo)
                }

            }

            is ToDoEvent.UpsertItem -> {
                viewModelScope.launch {
                    val todo = ToDo(
                        id = stateValues[0] as Int,
                        text = stateValues[1] as String,
                        checkboxState = if (stateValues[2] as Boolean) 1 else 0
                    )
                    dao.upsertItem(todo)
                }

            }
        }

    }
}