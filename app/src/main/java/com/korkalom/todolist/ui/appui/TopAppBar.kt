package com.korkalom.todolist.ui.appui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    content : @Composable () -> Unit,
) {
    TopAppBar(title = content, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.tertiary))
}


