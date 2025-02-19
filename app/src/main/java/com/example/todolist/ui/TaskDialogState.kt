package com.example.todolist.ui

import com.example.todolist.model.Task

sealed class TaskDialogState {
    object None : TaskDialogState()         // No dialog is shown
    object Add : TaskDialogState()          // Add Task dialog
    data class Edit(val task: Task) : TaskDialogState()     // Edit Task dialog for a specific task
    data class Delete(val task: Task) : TaskDialogState()   // Delete confirmation dialog for a specific task
}