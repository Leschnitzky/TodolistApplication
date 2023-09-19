package com.korkalom.todolist.ui.appui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.korkalom.todolist.ui.screens.home.HomeScreenIntent
import com.korkalom.todolist.ui.screens.home.HomeScreenState
import com.korkalom.todolist.ui.screens.home.HomeScreenVM
import com.korkalom.todolist.utils.BOTTOM_NAV
import com.korkalom.todolist.utils.BTN
import com.korkalom.todolist.utils.FAB
import com.korkalom.todolist.utils.Routes



val actionButtonList: List<BottomBarActionButtons> = listOf(
    BottomBarActionButtons(Icons.Filled.Home, "${BOTTOM_NAV}_${BTN}_"+ Routes.homeScreen, true),
    BottomBarActionButtons(Icons.Default.DateRange, "${BOTTOM_NAV}_${BTN}_" + Routes.dateRangeScreen, true),
    BottomBarActionButtons(Icons.Default.Person, "${BOTTOM_NAV}_${BTN}_" + Routes.contactScreen, false),
    BottomBarActionButtons(Icons.Default.Settings, "${BOTTOM_NAV}_${BTN}_" + Routes.settingsScreen, true)
)


val selectedButtonList: List<BottomBarActionButtons> = listOf(
    BottomBarActionButtons(Icons.Default.Delete, "${BOTTOM_NAV}_${BTN}_delete", true),
    BottomBarActionButtons(Icons.Default.Edit, "${BOTTOM_NAV}_${BTN}_edit", true),
    BottomBarActionButtons(Icons.Default.Info, "${BOTTOM_NAV}_${BTN}_details", true)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(
    modifier: Modifier,
    viewModel: HomeScreenVM,
    uiState: State<HomeScreenState>,
    containerColor: Color,
    navController: NavHostController
) = BottomAppBar(
    modifier = modifier.height(80.dp),
    tonalElevation = 22.dp,
    containerColor = containerColor,
) {
    var currentButtonList = actionButtonList
    if (uiState.value.numOfTasksSelected > 0) {
        currentButtonList = selectedButtonList
        currentButtonList[1].shouldShow = uiState.value.numOfTasksSelected <= 1
        currentButtonList[2].shouldShow = uiState.value.numOfTasksSelected <= 1
    }
    Row(
        modifier = modifier
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButtonsSection(
            viewModel = viewModel,
            actionButtonList = currentButtonList,
            state = uiState,
            navController = navController
        )
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
                .size(48.dp)
                .semantics {
                    contentDescription = "${BOTTOM_NAV}_${FAB}"
                },
                onClick = {
                   viewModel.intentChannel.trySend(
                       HomeScreenIntent.ClickedAdd
                   )
                },
                containerColor = if (uiState.value.numOfTasksSelected > 0) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceTint
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "${BOTTOM_NAV}_${FAB}",
                    tint = if (uiState.value.numOfTasksSelected > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
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
    navController: NavController,
    state: State<HomeScreenState>
) {
    IconButton(onClick = {
        navController.navigate(route = description.split("_")[2])
    }, modifier = modifier.size(48.dp)) {
        Icon(
            imageVector = vector,
            contentDescription = description,
            tint = if (state.value.numOfTasksSelected > 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        )
    }
}


data class BottomBarActionButtons(
    val icon: ImageVector,
    val contentDescription: String,
    var shouldShow: Boolean
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ActionButtonsSection(
    viewModel: HomeScreenVM,
    state: State<HomeScreenState>,
    actionButtonList: List<BottomBarActionButtons>,
    navController: NavController
) {
    for (actionButton in actionButtonList) {
        if (actionButton.shouldShow) {
            BottomBarIconButton(
                viewModel = viewModel,
                modifier = Modifier,
                vector = actionButton.icon,
                state = state,
                description = actionButton.contentDescription,
                navController = navController
            )
        }
    }
}



