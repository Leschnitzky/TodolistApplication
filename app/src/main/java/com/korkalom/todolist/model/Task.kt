package com.korkalom.todolist.model

import java.util.PriorityQueue

data class Task(
    val title: String = "No title",
    val description : String = "No description",
    val priority : Int = 1,
    val date: String = "No Date"
)
