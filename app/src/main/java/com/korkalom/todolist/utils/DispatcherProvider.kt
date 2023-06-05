package com.korkalom.todolist.utils

import kotlinx.coroutines.CoroutineDispatcher

data class DispatcherProvider (
    val dispatcherIO : CoroutineDispatcher,
    val dispatcherMain : CoroutineDispatcher,
    val dispatcherDefault : CoroutineDispatcher
)

