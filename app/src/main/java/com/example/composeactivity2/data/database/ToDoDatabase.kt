package com.example.composeactivity2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composeactivity2.data.dao.ToDoDao
import com.example.composeactivity2.domain.ToDo

@Database(
    entities = [ToDo::class],
    version = 1
)
abstract class ToDoDatabase : RoomDatabase() {
    abstract val dao: ToDoDao
}