package com.example.todolist.repository

import android.app.Application
import com.example.todolist.data.TaskDatabase
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(application: Application) {

    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
}