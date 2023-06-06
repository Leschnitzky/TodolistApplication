package com.korkalom.todolist.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.korkalom.todolist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel: MainScreenVM
) {
    Column(modifier = modifier.padding(8.dp)) {
        WelcomeUserSection(
            modifier
                .weight(1f)
        )
        Text(text = "Productivity graph section", modifier = modifier.weight(1f) )
        Text(text = "Tasks", modifier = modifier.weight(1f) )
        Text(text = "Today's plan", modifier = modifier.weight(1f) )

    }
}


@Composable
fun WelcomeUserSection(
    modifier: Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = androidx.core.R.drawable.ic_call_decline_low),
            contentDescription = "just an image",
            modifier = modifier
                .weight(1f)
        )
        TextSection(
            modifier
                .weight(10f)
        )


    }
}

@Composable
fun TextSection(modifier: Modifier) {
    Text(modifier = modifier, text = "Test")
}
