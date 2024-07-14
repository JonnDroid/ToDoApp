package com.example.composeactivity2.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val checkboxState: Int,
    val text: String
)