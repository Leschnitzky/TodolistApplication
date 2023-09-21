package com.korkalom.todolist.ui.screens.home

import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korkalom.todolist.model.Error
import com.korkalom.todolist.model.ErrorHandling
import com.korkalom.todolist.model.Task
import com.korkalom.todolist.ui.appui.DAY
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
                        val allErrors : ArrayList<ErrorHandling> = arrayListOf()
                        if (homeScreenIntent.task.priority == -1) {
                            allErrors.add(
                                ErrorHandling(
                                    Error.NO_PRIORITY_SELECTED, "No priority selected"
                                )
                            )

                        }
                        if (homeScreenIntent.task.title.isEmpty()) {
                            allErrors.add(
                                ErrorHandling(
                                    Error.TITLE_IS_EMPTY, "Title must not be empty"
                                )
                            )
                        }
                        if (homeScreenIntent.task.description.isEmpty()) {
                            allErrors.add(
                                ErrorHandling(
                                    Error.DESCRIPTION_IS_EMPTY, "Description must not be empty"
                                )
                            )
                        }
                        if(homeScreenIntent.task.date == null){
                            allErrors.add(
                                ErrorHandling(
                                    Error.DATE_IS_NOT_CHOSEN, "You must choose a date"
                                )
                            )
                        }

                        if (allErrors.isEmpty()) {
                            if(DateUtils.isToday(homeScreenIntent.task.date!!)){
                                val currentList = uiState.value.todayTasks
                                currentList.add(homeScreenIntent.task)
                                _uiState.value = uiState.value.copy(
                                    todayTasks = currentList,
                                    isSheetExpanded = false
                                )
                            } else if(DateUtils.isToday(
                                    homeScreenIntent.task.date - DAY
                                    )
                            ) {
                                val currentList = uiState.value.tomorrowTasks
                                currentList.add(homeScreenIntent.task)
                                _uiState.value = uiState.value.copy(
                                    tomorrowTasks = currentList,
                                    isSheetExpanded = false
                                )
                            } else {
                                val currentList = uiState.value.upcomingTasks
                                currentList.add(homeScreenIntent.task)
                                _uiState.value = uiState.value.copy(
                                    upcomingTasks = currentList,
                                    isSheetExpanded = false
                                )
                            }
                        } else {
                            _uiState.value = uiState.value.copy(
                                errors = allErrors
                            )
                        }
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
                            numOfTasksSelected = if (homeScreenIntent.selected) {
                                uiState.value.numOfTasksSelected + 1
                            } else {
                                uiState.value.numOfTasksSelected - 1
                            }
                        )
                    }

                    HomeScreenIntent.DeleteSelected -> TODO()
                }
            }
        }
    }
}