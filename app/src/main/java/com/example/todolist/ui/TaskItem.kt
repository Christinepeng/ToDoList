package com.example.todolist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.model.Task

@Composable
fun TaskItem(
    task: Task,
    onTaskClicked: (Task) -> Unit,
    onTaskCheckedboxClicked: (Task, Boolean) -> Unit,
    onEditTaskClicked: (Task) -> Unit,
    onDeleteTaskClicked: (Task) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClicked(task) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.clickable { onTaskClicked(task) },
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { isChecked -> onTaskCheckedboxClicked(task, isChecked) }
            )
            Text(text = task.title, modifier = Modifier.padding(start = 8.dp))
        }
        Row {
            IconButton(onClick = { onEditTaskClicked(task) }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Task"
                )
            }
            IconButton(onClick = { onDeleteTaskClicked(task) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Task"
                )
            }
        }
    }
}

@Composable
fun TaskList(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
    onTaskClicked: (Task) -> Unit,
    onTaskCheckedboxClicked: (Task, Boolean) -> Unit,
    onEditTaskClicked: (Task) -> Unit,
    onDeleteTaskClicked: (Task) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(tasks) { task ->
            TaskItem(
                task = task,
                onTaskClicked = onTaskClicked,
                onTaskCheckedboxClicked = onTaskCheckedboxClicked,
                onEditTaskClicked = onEditTaskClicked,
                onDeleteTaskClicked = onDeleteTaskClicked
            )
        }
    }
}
