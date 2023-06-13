package com.korkalom.todolist.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.github.ajalt.timberkt.Timber
import com.korkalom.todolist.R
import com.korkalom.todolist.utils.BTN
import com.korkalom.todolist.utils.PAGE_HOME

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            WelcomeUserSection()
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 80.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CardWithTitle(
                modifier = Modifier
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            viewModel.intentChannel.trySend(
                                HomeScreenIntent.LongClickedFirstCardWithTitle
                            )
                        }
                    )
                    .animateContentSize { initialValue, targetValue -> }
                    .heightIn(
                        min = dimensionResource(id = R.dimen.card_height_folded),
                        max = if (uiState.isTodayExpanded) {
                            dimensionResource(id = R.dimen.card_height_expanded)
                        } else if(uiState.isTomorrowExpanded || uiState.isUpcomingExpanded){
                            dimensionResource(id = R.dimen.card_height_folded)
                        } else {
                            dimensionResource(id = R.dimen.card_height_normal)
                        }
                    ).wrapContentHeight()
                    .padding(16.dp),
                title = "Today",
                numberOfTasks = uiState.upcomingTasks.size,
            ) {
                if (uiState.todayTasks.isEmpty()) {
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
            CardWithTitle(
                modifier = Modifier
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            viewModel.intentChannel.trySend(
                                HomeScreenIntent.LongClickedSecondCardWithTitle
                            )
                        }
                    )
                    .animateContentSize { initialValue, targetValue -> }
                    .heightIn(
                        min = dimensionResource(id = R.dimen.card_height_folded),
                        max = if (uiState.isTomorrowExpanded) {
                            dimensionResource(id = R.dimen.card_height_expanded)
                        } else if(uiState.isTodayExpanded || uiState.isUpcomingExpanded){
                            dimensionResource(id = R.dimen.card_height_folded)
                        } else {
                            dimensionResource(id = R.dimen.card_height_normal)
                        }
                    )
                    .wrapContentHeight()
                    .padding(16.dp),
                title = "Tommorow",
                numberOfTasks = uiState.upcomingTasks.size,
            ) {
                if (uiState.tomorrowTasks.isEmpty()) {
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
            CardWithTitle(
                modifier = Modifier
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            viewModel.intentChannel.trySend(
                                HomeScreenIntent.LongClickedThirdCardWithTitle
                            )
                        }
                    )
                    .animateContentSize { initialValue, targetValue -> }
                    .heightIn(
                        min = dimensionResource(id = R.dimen.card_height_folded),
                        max = if (uiState.isUpcomingExpanded) {
                            dimensionResource(id = R.dimen.card_height_expanded)
                        } else if(uiState.isTodayExpanded || uiState.isTomorrowExpanded){
                            dimensionResource(id = R.dimen.card_height_folded)
                        } else {
                            dimensionResource(id = R.dimen.card_height_normal)
                        }
                    )
                    .wrapContentHeight()
                    .padding(16.dp),
                title = "Upcoming",
                numberOfTasks = uiState.upcomingTasks.size,
            ) {
                if (uiState.upcomingTasks.isEmpty()) {
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardWithTitle(
    modifier: Modifier,
    title: String,
    numberOfTasks: Int,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    ) {
        Column(
            modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NotificationBadge(notificationCount = numberOfTasks)

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                            contentDescription = "Additional list menues"
                        )
                    }
                }

            }
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationBadge(notificationCount: Int) {
    Box(
        modifier = Modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notification Icon"
        )

        if (notificationCount > 0) {
            Badge(
                content = {
                    Text(
                        text = notificationCount.toString(),
                        color = MaterialTheme.colorScheme.surface
                    )
                },
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                contentColor = MaterialTheme.colorScheme.surface,
            )
        }
    }
}



