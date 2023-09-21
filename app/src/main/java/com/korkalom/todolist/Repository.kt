package com.korkalom.todolist

import com.korkalom.todolist.model.Task
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllTasks() : Flow<List<Task>>
    fun getTaskByID() : Flow<Task>
    fun deleteTasks(list: List<Int>) : Flow<String?>

}