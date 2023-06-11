package com.korkalom.todolist.ui.appui

import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.korkalom.todolist.utils.BOTTOM_NAV
import com.korkalom.todolist.utils.FAB


val buttonMap: Map<ImageVector, String> =
    mapOf(
        Icons.Filled.Home to "homeBtn1",
        Icons.Default.DateRange to "calendarBtn2",
        Icons.Default.Person to "contactBtn4",
        Icons.Default.Settings to "settingsBtn5",
    )


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(modifier: Modifier) = BottomAppBar(
    modifier = modifier.height(80.dp),
    tonalElevation = 22.dp,
    containerColor = MaterialTheme.colorScheme.primaryContainer,
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButtonsSection(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ).size(48.dp), onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "${BOTTOM_NAV}_${FAB}",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Composable
fun BottomBarIconButton(
    modifier: Modifier,
    vector: ImageVector,
    description: String
) {
    IconButton(onClick = { /*TODO*/ }, modifier = modifier.size(48.dp)) {
        Icon(
            imageVector = vector,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ActionButtonsSection(modifier: Modifier) {
    for (pair in buttonMap) {
        BottomBarIconButton(
            modifier = Modifier,
            vector = pair.key,
            description = pair.value
        )
    }
}



