package com.korkalom.todolist.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.korkalom.todolist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel: MainScreenVM
) {
    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
            WelcomeUserSection(
                Modifier
                    .fillMaxSize()
                    .heightIn(min = 60.dp)
                    .weight(0.5f)
            )
        Text(
            text = "Productivity graph section",
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(width = 1.dp, color = MaterialTheme.colorScheme.primary)
        )
        Text(
            text = "Tasks",
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(width = 1.dp, color = MaterialTheme.colorScheme.primary)
        )
        Text(
            text = "Today's plan",
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

    }
}


@Composable
fun WelcomeUserSection(
    modifier: Modifier
) {
    Row(modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Image(
            painter = painterResource(id = androidx.core.R.drawable.ic_call_decline_low),
            contentDescription = "just an image",
            modifier = Modifier
                .weight(1f)
                .size(50.dp)
                .widthIn(max = 50.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
        TextSection(
            Modifier.weight(6f)
        )


    }
}

@Composable
fun TextSection(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = LocalContext.current.getString(R.string.sample_text),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.wrapContentSize(),
            text = LocalContext.current.getString(R.string.sample_text),
            style = MaterialTheme.typography.bodySmall.copy(Color.Gray),
        )

    }
}
