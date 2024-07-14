package com.example.composeactivity2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.composeactivity2.domain.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Upsert
    suspend fun upsertItem(todo: ToDo)

    @Delete
    suspend fun deleteItem(todo: ToDo)

    @Query("SELECT * FROM todo ORDER BY checkboxState")
    fun getItemList(): Flow<List<ToDo>>
}