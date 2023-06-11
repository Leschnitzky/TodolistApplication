package com.korkalom.todolist.ui.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.github.ajalt.timberkt.Timber
import com.korkalom.todolist.utils.BTN
import com.korkalom.todolist.utils.PAGE_HOME

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier, viewModel: HomeScreenVM
) {
    val uiState = viewModel.uiState.collectAsState().value
    Timber.d(message = {
        "$uiState"
    })
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
        ) {
            WelcomeUserSection()
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardWithTitle(title = "Today") {
                if(uiState.todayTasks.isEmpty()){
                    Text(text = "No tasks available")
                } else {
                    LazyColumn {
                        items(uiState.todayTasks.size) {
                            CheckboxListItem(
                                title = uiState.todayTasks[it]
                            )
                            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.secondary)
                        }

                    }
                }
            }
            CardWithTitle(title = "Tomorrow") {
                if(uiState.tomorrowTasks.isEmpty()){
                    Text(text = "No tasks available")
                } else {
                    LazyColumn {
                        items(uiState.tomorrowTasks.size) {
                            CheckboxListItem(
                                title = uiState.tomorrowTasks[it]
                            )
                            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.secondary)
                        }

                    }
                }
            }
            CardWithTitle(title = "Upcoming") {
                if(uiState.upcomingTasks.isEmpty()){
                    Text(text = "No tasks available")
                } else {
                    LazyColumn {
                        items(uiState.upcomingTasks.size) {
                            CheckboxListItem(
                                title = uiState.upcomingTasks[it]
                            )
                            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.secondary)

                        }
                    }
                }
            }
        }
    }

}


@Composable
fun CheckboxListItem(
    painter: Painter = ColorPainter(color = MaterialTheme.colorScheme.secondary),
    title: String = "Headline",
    supportingText: String = "Supporting text",
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp)
                .size(18.dp), painter = painter, contentDescription = "Test"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Checkbox(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 24.dp),
            checked = true,
            onCheckedChange = {})
    }
}


@Composable
fun WelcomeUserSection(
    painter: Painter = ColorPainter(color = MaterialTheme.colorScheme.secondary),
    title: String = "ANON!",
    supportingText: String = "Welcome back,",
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp)
                .size(40.dp)
                .clip(CircleShape)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface),
            painter = painter,
            contentDescription = "Test"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column {
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Row(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "${PAGE_HOME}_${BTN}_notifications",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}


@Composable
fun CardWithTitle(title: String, content: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content()
        }
    }
}


