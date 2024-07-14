package com.example.composeactivity2.ui.presentation.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeactivity2.data.dao.ToDoDao
import com.example.composeactivity2.data.model.ToDoEvent
import com.example.composeactivity2.domain.ToDo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InputViewModel(private val dao: ToDoDao) : ViewModel() {
    private var _saveButtonState = MutableStateFlow(false)
    var saveButtonState = _saveButtonState

    private var _textLength = MutableStateFlow(0)
    var textLength = _textLength

    private var _text = MutableStateFlow("")
    var text = _text

    fun onTextChange(newText: String, length: Int) {
        _textLength.value = length
        if (length <= 250) _text.value = newText
        _saveButtonState.value = length != 0
    }

    fun saveEvent(event: ToDoEvent) {
        when (event) {
            is ToDoEvent.UpsertItem -> {
                val todo = ToDo(
                    text = _text.value,
                    checkboxState = 0
                )
                viewModelScope.launch {
                    dao.upsertItem(todo)
                }
                _saveButtonState.value = false
                _textLength.value = 0
                _text.value = ""
            }

            else -> {

            }
        }
    }
}