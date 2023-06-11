package com.korkalom.todolist.ui.screens.home

data class HomeScreenState(
    val name : String = "John Doe!",
    val todayTasks : ArrayList<String> = arrayListOf(),
    val tomorrowTasks : ArrayList<String> = arrayListOf(),
    val upcomingTasks : ArrayList<String> = arrayListOf()
) {

    override fun toString(): String {
        return "HomeScreenState(name='$name', todayTasks=$todayTasks, tomorrowTasks=$tomorrowTasks, upcomingTasks=$upcomingTasks)"
    }


}
