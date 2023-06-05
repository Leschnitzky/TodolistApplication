package com.korkalom.todolist

import android.app.Application
import com.github.ajalt.timberkt.Timber
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}