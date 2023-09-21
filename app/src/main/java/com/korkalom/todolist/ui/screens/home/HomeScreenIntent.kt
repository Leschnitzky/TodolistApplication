package com.korkalom.todolist.ui.screens.home

import com.korkalom.todolist.model.Task

sealed class HomeScreenIntent {
    object ClickedClear : HomeScreenIntent()
    object ClickedAdd : HomeScreenIntent()
    object AddDismissed: HomeScreenIntent()
    data class LongClickedElement(val selected: Boolean) : HomeScreenIntent()


    object LongClickedFirstCardWithTitle : HomeScreenIntent()
    object LongClickedSecondCardWithTitle : HomeScreenIntent()
    object LongClickedThirdCardWithTitle : HomeScreenIntent()


    data class AddedNewTask(val task : Task) : HomeScreenIntent()

    object DeleteSelected : HomeScreenIntent()

}
