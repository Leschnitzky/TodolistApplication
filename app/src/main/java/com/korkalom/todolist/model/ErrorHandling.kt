package com.korkalom.todolist.model

data class ErrorHandling(val errorCode: Error, val errorMsg: String)

enum class Error {
    NO_PRIORITY_SELECTED,
    TITLE_IS_EMPTY,
    DESCRIPTION_IS_EMPTY,
    DATE_IS_NOT_CHOSEN,
}
