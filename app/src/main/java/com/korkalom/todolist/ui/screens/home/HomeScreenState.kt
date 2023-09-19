package com.korkalom.todolist.ui.screens.home

import androidx.compose.material3.SheetState
import com.korkalom.todolist.R
import com.korkalom.todolist.model.Task

data class HomeScreenState(
    val index: Int = 0,
    val name: String = "John Doe!",
    val todayTasks: ArrayList<Task> = arrayListOf(),
    val tomorrowTasks: ArrayList<Task> = arrayListOf(),
    val upcomingTasks: ArrayList<Task> = arrayListOf(),
    val isTodayExpanded: Boolean = false,
    val isTomorrowExpanded: Boolean = false,
    val isUpcomingExpanded: Boolean = false,
    var isSheetExpanded: Boolean = false,
    val numOfTasksSelected: Int = 0
) {

    override fun toString(): String {
        return "HomeScreenState#$index(name='$name', todayTasks=$todayTasks, tomorrowTasks=$tomorrowTasks, upcomingTasks=$upcomingTasks)"
    }


}
