package com.korkalom.todolist.ui.screens.home

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.view.MenuItemCompat.getContentDescription
import com.github.ajalt.timberkt.Timber
import com.korkalom.todolist.R
import com.korkalom.todolist.model.Task
import com.korkalom.todolist.ui.appui.CardType
import com.korkalom.todolist.ui.appui.FilterMethod
import com.korkalom.todolist.ui.appui.formatter
import com.korkalom.todolist.ui.appui.getColorByPriority
import com.korkalom.todolist.utils.BTN
import com.korkalom.todolist.utils.PAGE_HOME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    modifier: Modifier, viewModel: HomeScreenVM
) {
    val uiState = viewModel.uiState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    var cardFolded = false
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
                            coroutineScope.launch {
                                viewModel.intentChannel.send(
                                    HomeScreenIntent.LongClickedFirstCardWithTitle
                                )
                            }
                        }
                    )
                    .animateContentSize()
                    .heightIn(
                        min = dimensionResource(id = R.dimen.card_height_folded),
                        max = if (uiState.isTodayExpanded) {
                            cardFolded = false
                            dimensionResource(id = R.dimen.card_height_expanded)
                        } else if (uiState.isTomorrowExpanded || uiState.isUpcomingExpanded) {
                            cardFolded = true
                            dimensionResource(id = R.dimen.card_height_folded)
                        } else {
                            cardFolded = false
                            dimensionResource(id = R.dimen.card_height_normal)
                        }
                    )
                    .wrapContentHeight()
                    .padding(16.dp)
                    .semantics {
                        contentDescription = "${PAGE_HOME}_$TODAY_CARD"
                    },
                title = "Today",
                numberOfTasks = uiState.todayTasks.size,
                isCardFolded = cardFolded,
                scope = coroutineScope,
                viewModel = viewModel,
                type = CardType.TODAY
            ) {
                if (uiState.todayTasks.isEmpty()) {
                    Text(text = "No tasks available")
                } else {
                    LazyColumn {
                        items(uiState.todayTasks.size) {
                            CheckboxListItem(
                                viewModel = viewModel,
                                modifier = Modifier.semantics {
                                    contentDescription = "${TODAY_CARD}_${LIST_ITEM}_#${it}"
                                },
                                task = uiState.todayTasks[it]
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
                            cardFolded = false
                            dimensionResource(id = R.dimen.card_height_expanded)
                        } else if (uiState.isTodayExpanded || uiState.isUpcomingExpanded) {
                            cardFolded = true
                            dimensionResource(id = R.dimen.card_height_folded)
                        } else {
                            cardFolded = false
                            dimensionResource(id = R.dimen.card_height_normal)
                        }
                    )
                    .wrapContentHeight()
                    .padding(16.dp)
                    .semantics {
                        contentDescription = "${PAGE_HOME}_$TOMORROW_CARD"
                    },
                title = "Tommorow",
                numberOfTasks = uiState.tomorrowTasks.size,
                isCardFolded = cardFolded,
                scope = coroutineScope,
                viewModel = viewModel,
                type = CardType.TOMORROW
            ) {
                if (uiState.tomorrowTasks.isEmpty()) {
                    Text(text = "No tasks available")
                } else {
                    LazyColumn {
                        items(uiState.tomorrowTasks.size) {
                            CheckboxListItem(
                                viewModel = viewModel,
                                modifier = Modifier.semantics {
                                    contentDescription = "${TOMORROW_CARD}_${LIST_ITEM}_#${it}"
                                },
                                task = uiState.tomorrowTasks[it]
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
                            cardFolded = false
                            dimensionResource(id = R.dimen.card_height_expanded)
                        } else if (uiState.isTodayExpanded || uiState.isTomorrowExpanded) {
                            cardFolded = true
                            dimensionResource(id = R.dimen.card_height_folded)
                        } else {
                            cardFolded = false
                            dimensionResource(id = R.dimen.card_height_normal)
                        }
                    )
                    .wrapContentHeight()
                    .padding(16.dp)
                    .semantics {
                        contentDescription = "${PAGE_HOME}_$UPCOMING_CARD"
                    },
                title = "Upcoming",
                numberOfTasks = uiState.upcomingTasks.size,
                isCardFolded = cardFolded,
                scope = coroutineScope,
                viewModel = viewModel,
                type = CardType.UPCOMING
            ) {
                if (uiState.upcomingTasks.isEmpty()) {
                    Text(text = "No tasks available")
                } else {
                    LazyColumn {
                        items(uiState.upcomingTasks.size) {
                            CheckboxListItem(
                                viewModel = viewModel,
                                modifier = Modifier.semantics {
                                    contentDescription = "${UPCOMING_CARD}_${LIST_ITEM}_#${it}"
                                },
                                task = uiState.upcomingTasks[it]
                            )
                            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.secondary)

                        }
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckboxListItem(
    modifier: Modifier,
    viewModel: HomeScreenVM,
    painter: Painter = ColorPainter(color = MaterialTheme.colorScheme.secondary),
    task: Task
) {

    var isSelected by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = {
                }
            )
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    isSelected = !isSelected
                    viewModel.intentChannel.trySend(
                        HomeScreenIntent.LongClickedElement(task,isSelected)
                    )
                }
            )
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.surfaceTint else MaterialTheme.colorScheme.surface
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp)
                .size(18.dp)
                .clip(CircleShape)
            , painter = painter, contentDescription = "Priority Icon",
            tint = getColorByPriority(task.priority),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = formatter.format(task.date),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
            var isChecked by remember{ mutableStateOf(false) }
            Checkbox(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 24.dp),
                checked = isChecked,
                onCheckedChange = {isChecked = !isChecked})
    }
}


@Composable
fun WelcomeUserSection(
    modifier: Modifier = Modifier.semantics {
        contentDescription = "${PAGE_HOME}_${WELCOME_CARD}"
    },
    painter: Painter = ColorPainter(color = MaterialTheme.colorScheme.secondary),
    title: String = "ANON!",
    supportingText: String = "Welcome back,",
) {
    Row(
        modifier = modifier.fillMaxWidth(),
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
    isCardFolded: Boolean = false,
    scope: CoroutineScope,
    viewModel: HomeScreenVM,
    type: CardType,
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
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        NotificationBadge(notificationCount = numberOfTasks)
                    }
                    Column(verticalArrangement = Arrangement.SpaceBetween) {
                        var expandedFilterMenu by remember { mutableStateOf(false) }
                        IconButton(onClick = {
                            if (!isCardFolded) {
                                expandedFilterMenu = !expandedFilterMenu
                            }
                        }) {

                            Icon(
                                painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                                contentDescription = "${PAGE_HOME}_${FILTER_BTN}"
                            )
                        }

                        DropdownMenu(
                            modifier = Modifier.semantics {
                                contentDescription = "${PAGE_HOME}_${FILTER_MENU}"
                            },
                            expanded = expandedFilterMenu,
                            onDismissRequest = { expandedFilterMenu = false }) {

                            DropdownMenuItem(text = { Text("By priority") }, onClick = {
                                scope.launch {
                                    viewModel.intentChannel.trySend(
                                        HomeScreenIntent.FilterList(FilterMethod(
                                            FilterMethod.Type.PRIORITY,
                                            type
                                        ))
                                    )
                                }
                            })
                            DropdownMenuItem(text = { Text("By due date") }, onClick = {
                                scope.launch {
                                    viewModel.intentChannel.trySend(
                                        HomeScreenIntent.FilterList(FilterMethod(
                                            FilterMethod.Type.DATE,
                                            type
                                        ))
                                    )
                                }
                            })
                            DropdownMenuItem(text = { Text("By name") }, onClick = {
                                scope.launch {
                                    viewModel.intentChannel.trySend(
                                        HomeScreenIntent.FilterList(FilterMethod(
                                            FilterMethod.Type.NAME,
                                            type
                                        ))
                                    )
                                }
                            })
                        }
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



