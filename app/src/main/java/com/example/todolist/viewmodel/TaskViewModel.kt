package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.todolist.model.Task
import com.example.todolist.repository.TaskRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository = TaskRepository(application)
    val allTasks: LiveData<List<Task>> = repository.allTasks

    fun insert(task: Task) = repository.insert(task)

    fun update(task: Task) = repository.update(task)

    fun delete(task: Task) = repository.delete(task)
}