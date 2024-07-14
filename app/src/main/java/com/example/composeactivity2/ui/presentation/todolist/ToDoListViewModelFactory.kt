package com.example.composeactivity2.ui.presentation.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composeactivity2.data.dao.ToDoDao

class ToDoListViewModelFactory(private val dao: ToDoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToDoListViewModel(dao) as T
    }
}