package com.korkalom.todolist.repository

import androidx.room.CoroutinesRoom
import androidx.room.RoomDatabase
import com.korkalom.todolist.model.CustomResult
import com.korkalom.todolist.model.Error
import com.korkalom.todolist.model.ErrorHandling
import com.korkalom.todolist.model.Task
import com.korkalom.todolist.model.room.TaskDatabase
import com.korkalom.todolist.model.room.entities.TaskTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDB: TaskDatabase,
) : Repository {

    override suspend fun getAllTasks(): CustomResult<List<Task>> {
        return try {
            CustomResult(
                value = roomDB.taskDao().getAll(),
                errorHandling = null
            )
        } catch (e: Exception) {
            CustomResult(
                value = listOf(),
                errorHandling = ErrorHandling(
                    Error.ROOM_ERROR,
                    e.message!!
                )
            )
        }
    }

    override fun getTaskByID(): Flow<CustomResult<Task>> {
        TODO("Not yet implemented")
    }

    override fun deleteTasks(list: List<Int>): Flow<CustomResult<String>> {
        TODO("Not yet implemented")
    }

    override fun addTask(task: Task): Flow<CustomResult<Long>> {
        try {
            return flow {
                emit(
                    CustomResult(
                        roomDB.taskDao().insertTask(
                            TaskTable(
                                title = task.title,
                                description = task.description,
                                date = task.date!!,
                                priority = task.priority
                            )
                        ),
                        null
                    )
                )
            }
        } catch (e: Exception) {
            return flow {
                emit(
                    CustomResult(
                        value = -1,
                        ErrorHandling(
                            Error.ROOM_ERROR,
                            e.message!!
                        )
                    )
                )
            }
        }
    }
}