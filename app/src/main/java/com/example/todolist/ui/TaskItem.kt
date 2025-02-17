package com.example.todolist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.model.Task

@Composable
fun TaskItem(
    task: Task,
    onTaskClicked: (Task) -> Unit,
    onTaskCheckedboxClicked: (Task, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClicked(task) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { isChecked ->
                onTaskCheckedboxClicked(task, isChecked)
            }
        )
        Text(
            text = task.title,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun TaskList(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
    onTaskClicked: (Task) -> Unit,
    onTaskCheckedboxClicked: (Task, Boolean) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(tasks) { task ->
            TaskItem(
                task = task,
                onTaskClicked = onTaskClicked,
                onTaskCheckedboxClicked = onTaskCheckedboxClicked
            )
        }
    }
}
