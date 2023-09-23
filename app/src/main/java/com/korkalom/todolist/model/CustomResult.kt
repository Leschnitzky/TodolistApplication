package com.korkalom.todolist.model

data class CustomResult<T>(val value: T, val errorHandling: ErrorHandling?)
