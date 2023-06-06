package com.korkalom.todolist.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import com.korkalom.todolist.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainScreenState(val name : String)

@HiltViewModel
public class MainScreenVM @Inject constructor(
    private val dispatcherProvider : DispatcherProvider
) : ViewModel() {


    private val _uiState = MutableStateFlow(MainScreenState(""))
    val uiState : StateFlow<MainScreenState> = _uiState.asStateFlow()


    val intentChannel = Channel<HomeScreenIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(dispatcherProvider.dispatcherIO) {
            intentChannel.consumeAsFlow().collect {
                Timber.d( message = {
                    "GOT: $it"
                })
                when(it) {

                    is HomeScreenIntent.UpdatedText -> {
                        _uiState.value = MainScreenState(it.newText)
                    }

                    is HomeScreenIntent.ClickedButton -> {
                        _uiState.value = MainScreenState("")
                    }
                }
            }
        }
    }
}