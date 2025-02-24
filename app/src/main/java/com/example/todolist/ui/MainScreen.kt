package com.example.todolist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.todolist.model.Task
import com.example.todolist.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    taskViewModel: TaskViewModel,
    onTaskClicked: (Task) -> Unit = {}
) {
    // State for snackbars
    val snackbarHostState = remember { SnackbarHostState() }

    // Collect the Flow from the ViewModel as State
    val tasks by taskViewModel.allTasks.collectAsState(initial = emptyList())

    // Use a sealed class state to control which dialog is visible
    var dialogState by remember { mutableStateOf<TaskDialogState>(TaskDialogState.None) }


    Scaffold(
        // Material 3 uses `snackbarHost` instead of `scaffoldState`
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("To-Do List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { dialogState = TaskDialogState.Add }) {
                Text("+")
            }
        }
    )
    { innerPadding ->
        // Pass `innerPadding` to your content’s Modifier to respect insets
        TaskList(
            tasks = tasks,
            modifier = Modifier.padding(innerPadding),
            onTaskClicked = onTaskClicked,
            onTaskCheckedboxClicked = { task, isChecked ->
                // Create a new copy with the updated value instead of mutating the existing object.
                taskViewModel.update(task.copy(isCompleted = isChecked))
            },
            onEditTaskClicked = { task ->
                dialogState = TaskDialogState.Edit(task)
            },
            onDeleteTaskClicked = { task ->
                dialogState = TaskDialogState.Delete(task)
            }
        )
    }

    // Show the appropriate dialog based on the current dialog state
    when (val state = dialogState) {
        is TaskDialogState.Add -> {
            AddTaskDialog(
                onDismiss = { dialogState = TaskDialogState.None },
                onAddTask = { title ->
                    if (title.isNotBlank()) {
                        taskViewModel.insert(Task(title = title, isCompleted = false))
                    }
                    dialogState = TaskDialogState.None
                }
            )
        }
        is TaskDialogState.Edit -> {
            EditTaskDialog(
                task = state.task,
                onDismiss = { dialogState = TaskDialogState.None },
                onEditTask = { updatedTitle ->
                    taskViewModel.update(state.task.copy(title = updatedTitle))
                    dialogState = TaskDialogState.None
                }
            )
        }
        is TaskDialogState.Delete -> {
            DeleteConfirmationDialog(
                task = state.task,
                onDismiss = { dialogState = TaskDialogState.None },
                onConfirmDelete = {
                    taskViewModel.delete(state.task)
                    dialogState = TaskDialogState.None
                }
            )
        }
        TaskDialogState.None -> {
            // No dialog to show
        }
    }
}

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onAddTask: (String) -> Unit
) {
    // Local text state for the OutlinedTextField
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Task") },
        text = {
            // Use the Material 3 OutlinedTextField
            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                label = { Text("Task title") }
            )
        },
        confirmButton = {
            TextButton(onClick = { onAddTask(textState.text) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EditTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onEditTask: (String) -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue(task.title)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Task") },
        text = {
            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                label = { Text("Task title") }
            )
        },
        confirmButton = {
            TextButton(onClick = { onEditTask(textState.text) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DeleteConfirmationDialog(
    task: Task,
    onDismiss: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Task") },
        text = { Text("Are you sure you want to delete \"${task.title}\"?") },
        confirmButton = {
            TextButton(onClick = onConfirmDelete) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}