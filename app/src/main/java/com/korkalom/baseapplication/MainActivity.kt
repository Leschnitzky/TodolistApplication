package com.korkalom.baseapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.korkalom.baseapplication.ui.appui.MyAppBar
import com.korkalom.baseapplication.ui.screens.main.MainScreen
import com.korkalom.baseapplication.ui.screens.main.MainScreenVM
import com.korkalom.baseapplication.ui.theme.BaseApplicationTheme
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
            topBar = {
                MyAppBar {
                    Text("Test")
                }
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
