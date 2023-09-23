package com.korkalom.todolist.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.korkalom.todolist.model.room.entities.TaskTable

const val TASK_DB_NAME = "tasks"
@Database(entities = [TaskTable::class], version = 3)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}