package com.korkalom.todolist.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korkalom.todolist.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
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
            intentChannel.consumeAsFlow().collect { homeScreenIntent ->
                when (homeScreenIntent) {

                    is HomeScreenIntent.ClickedClear -> {
                        _uiState.value = HomeScreenState()
                    }

                    is HomeScreenIntent.ClickedAdd -> {
                        val prev = uiState.value;
                        val tasks = prev.todayTasks + arrayListOf(homeScreenIntent.title)
                        _uiState.value = HomeScreenState(
                            index = prev.index + 1,
                            name = prev.name,
                            todayTasks = tasks as ArrayList<String>,
                            tomorrowTasks = tasks,
                            upcomingTasks = tasks,
                            isTodayExpanded = prev.isTodayExpanded,
                            isTomorrowExpanded = prev.isTomorrowExpanded,
                            isUpcomingExpanded = prev.isUpcomingExpanded
                        )
                    }

                    HomeScreenIntent.LongClickedFirstCardWithTitle -> {
                        val prev = uiState.value;
                        _uiState.value = HomeScreenState(
                            index = prev.index + 1,
                            name = prev.name,
                            todayTasks = prev.todayTasks as ArrayList<String>,
                            tomorrowTasks = prev.tomorrowTasks,
                            upcomingTasks = prev.upcomingTasks,
                            isTodayExpanded = true,
                            isTomorrowExpanded = false,
                            isUpcomingExpanded = false
                        )
                    }

                    HomeScreenIntent.LongClickedSecondCardWithTitle -> {
                        val prev = uiState.value;
                        _uiState.value = HomeScreenState(
                            index = prev.index + 1,
                            name = prev.name,
                            todayTasks = prev.todayTasks as ArrayList<String>,
                            tomorrowTasks = prev.tomorrowTasks,
                            upcomingTasks = prev.upcomingTasks,
                            isTodayExpanded = false,
                            isTomorrowExpanded = true,
                            isUpcomingExpanded = false
                        )
                    }

                    HomeScreenIntent.LongClickedThirdCardWithTitle -> {
                        val prev = uiState.value;
                        _uiState.value = HomeScreenState(
                            index = prev.index + 1,
                            name = prev.name,
                            todayTasks = prev.todayTasks as ArrayList<String>,
                            tomorrowTasks = prev.tomorrowTasks,
                            upcomingTasks = prev.upcomingTasks,
                            isTodayExpanded = false,
                            isTomorrowExpanded = false,
                            isUpcomingExpanded = true
                        )
                    }
                }
            }
        }
    }
}