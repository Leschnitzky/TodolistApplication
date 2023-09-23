package com.korkalom.todolist.model

data class Task(
    val taskID: Long = -1,
    val title: String = "No title",
    val description: String = "No description",
    val priority: Int = 1,
    val date: Long? = null
)
