package com.korkalom.todolist.ui.screens.home

import androidx.compose.material3.SheetState
import com.korkalom.todolist.R
import com.korkalom.todolist.model.ErrorHandling
import com.korkalom.todolist.model.Task

data class HomeScreenState(
    val index: Int = 0,
    val name: String = "John Doe!",
    val todayTasks: List<Task> = listOf(),
    val tomorrowTasks: List<Task> = listOf(),
    val upcomingTasks: List<Task> = listOf(),
    val selectedTasks: List<Task> = listOf(),
    val isTodayExpanded: Boolean = false,
    val isTomorrowExpanded: Boolean = false,
    val isUpcomingExpanded: Boolean = false,
    var isSheetExpanded: Boolean = false,
    val errors: List<ErrorHandling> = listOf()
) {

    override fun toString(): String {
        return "HomeScreenState#$index(name='$name', todayTasks=$todayTasks, tomorrowTasks=$tomorrowTasks, upcomingTasks=$upcomingTasks)"
    }


}
