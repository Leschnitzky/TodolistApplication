package com.korkalom.todolist.ui.screens.home

sealed class HomeScreenIntent {
    data class UpdatedText(val newText : String) : HomeScreenIntent()
    object ClickedButton : HomeScreenIntent()
}

