package com.korkalom.todolist.utils

sealed class Routes {
    companion object{
        const val homeScreen = "homeScreen"
        const val addScreen = "addScreen"
        const val dateRangeScreen = "dataRangeScreen"
        const val contactScreen = "contactScreen"
        const val settingsScreen = "settingsScreen"
    }
}