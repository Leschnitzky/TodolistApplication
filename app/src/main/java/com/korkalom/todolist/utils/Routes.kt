package com.korkalom.todolist.utils

sealed class Routes {
    companion object{
        const val NAV_PREFIX = "nav"
        const val homeScreen = NAV_PREFIX + "home"
        const val statisticsScreen = NAV_PREFIX + "statistics"
        const val contactScreen = NAV_PREFIX + "contacts"
        const val settingsScreen = NAV_PREFIX + "settings"
        const val detailsScreen = NAV_PREFIX + "details"
        const val editScreen = NAV_PREFIX + "edit"
    }
}