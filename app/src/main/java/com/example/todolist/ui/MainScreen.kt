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
import androidx.compose.runtime.livedata.observeAsState
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

    // State to show/hide the "Add Task" dialog
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        // Material 3 uses `snackbarHost` instead of `scaffoldState`
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("To-Do List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true }
            ) {
                Text("+")
            }
        }
    ) { innerPadding ->
        // Pass `innerPadding` to your content’s Modifier to respect insets
        TaskList(
            tasks = tasks,
            modifier = Modifier.padding(innerPadding),
            onTaskClicked = onTaskClicked,
            onTaskCheckedboxClicked = { task, isChecked ->
                task.isCompleted = isChecked
                taskViewModel.update(task)
            }
        )
    }

    if (showDialog) {
        AddTaskDialog(
            onDismiss = { showDialog = false },
            onAddTask = { title ->
                if (title.isNotBlank()) {
                    taskViewModel.insert(Task(title = title, isCompleted = false))
                }
                showDialog = false
            }
        )
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
