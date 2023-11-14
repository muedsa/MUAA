package com.muedsa.muaa.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.muedsa.muaa.model.Aria2Task

@Composable
fun Aria2TaskListWidget(
    modifier: Modifier = Modifier, taskList: List<Aria2Task>
) {

    TvLazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp)
    ) {
        items(items = taskList) {
            Aria2TaskCard(modifier = Modifier.padding(bottom = 20.dp), task = it)
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Aria2TaskCard(
    modifier: Modifier = Modifier, task: Aria2Task
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp),
                progress = task.progress,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Progress: ${"%.2f".format(task.progress * 100)}%, Speed: ${task.downloadSpeed}, Connections:${task.connections}",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}