package com.korkalom.todolist.ui.screens.home

import com.korkalom.todolist.R

data class HomeScreenState(
    val index: Int = 0,
    val name: String = "John Doe!",
    val todayTasks: ArrayList<String> = arrayListOf(),
    val tomorrowTasks: ArrayList<String> = arrayListOf(),
    val upcomingTasks: ArrayList<String> = arrayListOf(),
    val isTodayExpanded: Boolean = false,
    val isTomorrowExpanded: Boolean = false,
    val isUpcomingExpanded: Boolean = false
) {

    override fun toString(): String {
        return "HomeScreenState#$index(name='$name', todayTasks=$todayTasks, tomorrowTasks=$tomorrowTasks, upcomingTasks=$upcomingTasks)"
    }


}
