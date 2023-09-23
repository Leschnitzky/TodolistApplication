package com.korkalom.todolist.ui.screens.home

import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import com.korkalom.todolist.model.Error
import com.korkalom.todolist.model.ErrorHandling
import com.korkalom.todolist.model.Task
import com.korkalom.todolist.repository.Repository
import com.korkalom.todolist.repository.RoomRepositoryImpl
import com.korkalom.todolist.ui.appui.DAY
import com.korkalom.todolist.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
public class HomeScreenVM @Inject constructor(
    private val dispatcherProvider: DispatcherProvider, private val repository: Repository
) : ViewModel() {


    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()


    val intentChannel = Channel<HomeScreenIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
        loadValuesFromDB()
    }

    private fun loadValuesFromDB() {
        viewModelScope.launch {
            intentChannel.send(
                HomeScreenIntent.LoadTasks
            )
        }
    }

    private fun updateUITasks(scope: CoroutineScope) {
        scope.launch {
            repository.getAllTasks().also { res ->
                if (res.errorHandling == null) {
                    //Task successful

                    val list = res.value.ifEmpty { arrayListOf() }

                    val todayList = list.filter {
                        DateUtils.isToday(it.date!!)
                    }

                    val tomorrowList = list.filter {
                        DateUtils.isToday(it.date!! - DAY)
                    }

                    val upcomingList = list - (todayList + tomorrowList).toSet()

                    _uiState.value = uiState.value.copy(
                        todayTasks = todayList,
                        tomorrowTasks = tomorrowList,
                        upcomingTasks = upcomingList,
                        isSheetExpanded = false
                    )
                } else {
                    Timber.d(Throwable(message = res.errorHandling.errorMsg))
                }
            }
        }
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
                        val allErrors: ArrayList<ErrorHandling> = arrayListOf()
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
                        if (homeScreenIntent.task.date == null) {
                            allErrors.add(
                                ErrorHandling(
                                    Error.DATE_IS_NOT_CHOSEN, "You must choose a date"
                                )
                            )
                        }

                        if (allErrors.isEmpty()) {
                            repository.addTask(homeScreenIntent.task).collect { res ->
                                val scope = this
                                if (res.errorHandling == null) {
                                    with(dispatcherProvider.dispatcherMain){
                                        updateUITasks(scope)
                                    }
                                } else {
                                    Timber.d(Throwable(message = "GOT HERE!!!"))
                                    allErrors.add(
                                        res.errorHandling
                                    )
                                    _uiState.value = uiState.value.copy(
                                        errors = allErrors
                                    )
                                }
                            }
                        } else {
                            _uiState.value = uiState.value.copy(
                                errors = allErrors
                            )
                        }
                    }


                    is HomeScreenIntent.LongClickedFirstCardWithTitle -> {
                        _uiState.value = uiState.value.copy(
                            isTodayExpanded = !uiState.value.isTodayExpanded,
                            isTomorrowExpanded = false,
                            isUpcomingExpanded = false
                        )
                    }

                    is HomeScreenIntent.LongClickedSecondCardWithTitle -> {
                        _uiState.value = uiState.value.copy(
                            isTodayExpanded = false,
                            isTomorrowExpanded = !uiState.value.isTomorrowExpanded,
                            isUpcomingExpanded = false
                        )
                    }

                    is HomeScreenIntent.LongClickedThirdCardWithTitle -> {
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

                    is HomeScreenIntent.DeleteSelected -> {

                    }
                    is HomeScreenIntent.LoadTasks -> {
                        viewModelScope.launch {
                            val scope = this
                            with(dispatcherProvider.dispatcherIO) {
                                updateUITasks(scope)
                            }
                        }
                    }
                }
            }
        }
    }
}