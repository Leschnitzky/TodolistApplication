package com.korkalom.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.korkalom.todolist.ui.appui.MyBottomBar
import com.korkalom.todolist.ui.screens.home.MainScreen
import com.korkalom.todolist.ui.theme.BaseApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

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
        Scaffold(
            bottomBar = {
                        MyBottomBar(
                            modifier = Modifier.fillMaxSize()
                        )
            },
            content = { padding -> run {
                MainScreen(
                    modifier = Modifier.padding(padding),
                    viewModel = viewModel()
                )
            }
            }
        )
    }
}
