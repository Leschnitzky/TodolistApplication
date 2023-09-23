package com.korkalom.todolist.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.korkalom.todolist.model.Task
import com.korkalom.todolist.model.room.entities.TaskTable
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskTable")
    suspend fun getAll(): List<Task>

    @Query("SELECT * FROM TaskTable WHERE taskID LIKE :taskID LIMIT 1")
    suspend fun getTaskByID(taskID: Int) : Task

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskTable) : Long

    @Delete
    suspend fun removeTask(task: TaskTable) : Int
}