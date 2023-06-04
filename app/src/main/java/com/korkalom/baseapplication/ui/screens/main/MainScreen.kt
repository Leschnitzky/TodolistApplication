package com.korkalom.baseapplication.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel : MainScreenVM
){
    Column(modifier = modifier) {
        val uiState = viewModel.uiState.collectAsState()
        Text(text = "Hello ${uiState.value.name}!")
        OutlinedTextField(
            modifier = modifier,
            value = uiState.value.name,
            onValueChange = {
                viewModel.intentChannel.trySend(
                    MainScreenIntent.UpdatedText(it)
                ) })
        Button(onClick = {
            viewModel.intentChannel.trySend (
                MainScreenIntent.ClickedButton
            )
        },
        ) {
            Text(text = "Clear")
        }
    }
}