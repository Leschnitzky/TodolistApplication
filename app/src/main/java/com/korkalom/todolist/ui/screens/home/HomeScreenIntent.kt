package com.korkalom.todolist.ui.screens.home

sealed class HomeScreenIntent {
    object ClickedClear : HomeScreenIntent()
    data class ClickedAdd (val title: String) : HomeScreenIntent()
}

