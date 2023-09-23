package com.korkalom.todolist.model.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskTable(
    @PrimaryKey(autoGenerate = true) val taskID: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description : String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "priority") val priority: Int
)