package com.korkalom.todolist.ui.screens.home

import com.korkalom.todolist.model.Task
import com.korkalom.todolist.ui.appui.FilterMethod

sealed class HomeScreenIntent {
    object ClickedClear : HomeScreenIntent()
    object ClickedAdd : HomeScreenIntent()
    object AddDismissed: HomeScreenIntent()
    data class LongClickedElement(val task: Task, val isSelected: Boolean) : HomeScreenIntent()
    data class FilterList(val filterMethod: FilterMethod) : HomeScreenIntent()

    object LongClickedFirstCardWithTitle : HomeScreenIntent()
    object LongClickedSecondCardWithTitle : HomeScreenIntent()
    object LongClickedThirdCardWithTitle : HomeScreenIntent()


    data class AddedNewTask(val task : Task) : HomeScreenIntent()

    object DeleteSelected : HomeScreenIntent()
    object LoadTasks: HomeScreenIntent()

}
