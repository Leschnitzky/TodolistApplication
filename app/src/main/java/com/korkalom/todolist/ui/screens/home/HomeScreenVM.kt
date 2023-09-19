package com.korkalom.todolist.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korkalom.todolist.model.Task
import com.korkalom.todolist.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
public class HomeScreenVM @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {


    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()


    val intentChannel = Channel<HomeScreenIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(dispatcherProvider.dispatcherIO) {
            intentChannel.receiveAsFlow().collect { homeScreenIntent ->
                when (homeScreenIntent) {

                    is HomeScreenIntent.ClickedClear -> {
                        _uiState.value = HomeScreenState()
                    }

                    is HomeScreenIntent.ClickedAdd -> {
                          _uiState.value = uiState.value.copy(isSheetExpanded = true)
                    }

                    is HomeScreenIntent.AddDismissed -> {
                        _uiState.value = uiState.value.copy(isSheetExpanded = false)
                    }

                    is HomeScreenIntent.AddedNewTask -> {
                        val currentList = uiState.value.upcomingTasks
                        currentList.add(homeScreenIntent.task)
                        _uiState.value.copy(
                            upcomingTasks = currentList,
                            isSheetExpanded = false
                        )
                    }


                    HomeScreenIntent.LongClickedFirstCardWithTitle -> {
                        _uiState.value = uiState.value.copy(
                            isTodayExpanded = !uiState.value.isTodayExpanded,
                            isTomorrowExpanded = false,
                            isUpcomingExpanded = false
                        )
                    }

                    HomeScreenIntent.LongClickedSecondCardWithTitle -> {
                        _uiState.value = uiState.value.copy(
                            isTodayExpanded = false,
                            isTomorrowExpanded = !uiState.value.isTomorrowExpanded,
                            isUpcomingExpanded = false
                        )
                    }

                    HomeScreenIntent.LongClickedThirdCardWithTitle -> {
                        _uiState.value = uiState.value.copy(
                            isTodayExpanded = false,
                            isTomorrowExpanded = false,
                            isUpcomingExpanded = !uiState.value.isTomorrowExpanded
                        )
                    }

                    is HomeScreenIntent.LongClickedElement -> {
                        _uiState.value = uiState.value.copy(
                            numOfTasksSelected = if (homeScreenIntent.selected) uiState.value.numOfTasksSelected + 1 else uiState.value.numOfTasksSelected - 1
                        )
                    }

                }
            }
        }
    }
}