package com.korkalom.todolist.repository

import com.korkalom.todolist.model.CustomResult
import com.korkalom.todolist.model.Task
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getAllTasks() : CustomResult<List<Task>>
    fun getTaskByID() : Flow<CustomResult<Task>>
    fun deleteTasks(list: List<Long>) : Flow<CustomResult<String>>
    fun addTask(task: Task) : Flow<CustomResult<Long>>
}