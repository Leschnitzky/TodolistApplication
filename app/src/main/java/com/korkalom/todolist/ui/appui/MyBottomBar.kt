package com.korkalom.todolist.ui.appui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.korkalom.todolist.ui.screens.home.HomeScreenIntent
import com.korkalom.todolist.ui.screens.home.HomeScreenVM
import com.korkalom.todolist.utils.BOTTOM_NAV
import com.korkalom.todolist.utils.BTN
import com.korkalom.todolist.utils.FAB

data class BottomBarActionButtons(val icon : ImageVector, val contentDescription : String, val shouldShow : Boolean)


val actionButtonList : List<BottomBarActionButtons> = listOf(
    BottomBarActionButtons(Icons.Filled.Home, "${BOTTOM_NAV}_${BTN}_home", true),
    BottomBarActionButtons(Icons.Default.DateRange, "${BOTTOM_NAV}_${BTN}_cal", true),
    BottomBarActionButtons(Icons.Default.Person, "${BOTTOM_NAV}_${BTN}_contact", false),
    BottomBarActionButtons(Icons.Default.Settings, "${BOTTOM_NAV}_${BTN}_settings", true)
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(modifier: Modifier, viewModel : HomeScreenVM) = BottomAppBar(
    modifier = modifier.height(80.dp),
    tonalElevation = 22.dp,
    containerColor = MaterialTheme.colorScheme.primaryContainer,
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButtonsSection(Modifier.weight(1f), viewModel)
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ).size(48.dp), onClick = { viewModel.intentChannel.trySend(
                HomeScreenIntent.ClickedAdd("Test")
            )}) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "${BOTTOM_NAV}_${FAB}",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Composable
fun BottomBarIconButton(
    modifier: Modifier,
    vector: ImageVector,
    description: String,
    viewModel: HomeScreenVM,
) {
    IconButton(onClick = {
        viewModel.intentChannel.trySend(
            HomeScreenIntent.ClickedClear
        )
    }, modifier = modifier.size(48.dp)) {
        Icon(
            imageVector = vector,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ActionButtonsSection(modifier: Modifier, viewModel: HomeScreenVM) {
    for (actionButton in actionButtonList) {
        if(actionButton.shouldShow){
            BottomBarIconButton(
                viewModel = viewModel,
                modifier = Modifier,
                vector = actionButton.icon,
                description = actionButton.contentDescription
            )
        }
    }
}



