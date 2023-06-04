package com.korkalom.baseapplication.ui.screens.main

sealed class MainScreenIntent {
    data class UpdatedText(val newText : String) : MainScreenIntent()
    object ClickedButton : MainScreenIntent()
}

