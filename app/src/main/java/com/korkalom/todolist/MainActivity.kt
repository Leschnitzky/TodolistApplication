package com.korkalom.todolist

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.korkalom.todolist.ui.appui.MyBottomBar
import com.korkalom.todolist.ui.screens.home.AddScreen
import com.korkalom.todolist.ui.screens.daterange.DateRangeScreen
import com.korkalom.todolist.ui.screens.details.DetailsScreen
import com.korkalom.todolist.ui.screens.home.HomeScreenIntent
import com.korkalom.todolist.ui.screens.home.HomeScreenVM
import com.korkalom.todolist.ui.screens.home.MainScreen
import com.korkalom.todolist.ui.screens.settings.SettingsScreen
import com.korkalom.todolist.ui.theme.BaseApplicationTheme
import com.korkalom.todolist.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(){
    BaseApplicationTheme {
        val navController = rememberNavController()
        val viewModel : HomeScreenVM = viewModel()
        val uiState = viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        val state = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { true }
        )

        var contColor : Color = if(uiState.value.numOfTasksSelected > 0){
            MaterialTheme.colorScheme.surfaceTint
        } else {
            MaterialTheme.colorScheme.primaryContainer
        }
        BottomSheetScaffold(
            sheetPeekHeight = 0.dp,
            sheetDragHandle = {},
            sheetContent = {
                if(uiState.value.isSheetExpanded) {
                    ModalBottomSheet(
                        sheetState = state,
                        modifier = Modifier
                            .heightIn(max = 700.dp)
                            .fillMaxSize()
                        ,

                        onDismissRequest = {
                            scope.launch {
                                viewModel.intentChannel.trySend(
                                    HomeScreenIntent.AddDismissed
                                )
                            }
                        },
                    ) {
                    AddScreen(
                        viewModel
                    )
                }

            }
        }) {
            Scaffold(
                bottomBar = {
                    MyBottomBar(
                        modifier = Modifier,
                        viewModel = viewModel(),
                        uiState = uiState,
                        containerColor = contColor,
                        navController = navController
                    )
                },
                content = { padding -> run {
                    NavHost(navController = navController, startDestination = Routes.homeScreen){
                        composable(Routes.homeScreen) {
                            MainScreen(
                                modifier = Modifier.padding(padding),
                                viewModel = viewModel
                            )
                        }

                        composable(Routes.addScreen) {
                            AddScreen(
                                viewModel
                            )
                        }
                        composable("detailsScreen") {
                            DetailsScreen(

                            )
                        }
                        composable(Routes.settingsScreen) {
                            SettingsScreen(

                            )
                        }
                        composable(Routes.dateRangeScreen) {
                            DateRangeScreen(

                            )
                        }
                    }
                }
                }
            )
        }
    }

}

