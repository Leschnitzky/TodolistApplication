package com.korkalom.todolist.ui.appui

import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp



val buttonMap : Map< ImageVector, String> =
    mapOf(
        Icons.Filled.Home to "homeBtn1" ,
        Icons.Default.DateRange to "calendarBtn2",
        Icons.Filled.Add to "addBtn3",
        Icons.Default.Person to "contactBtn4",
        Icons.Default.Settings to "settingsBtn5",
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(modifier: Modifier) = BottomAppBar {
    Row(
        modifier = modifier
    ) {
        for (pair in buttonMap) {
            BottomBarIconButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                vector = pair.key,
                description = pair.value
            )
        }
    }
}


@Composable
fun BottomBarIconButton(
    modifier: Modifier,
    vector: ImageVector,
    description : String
) {
    IconButton(onClick = { /*TODO*/ }, modifier = modifier ) {
        Icon(
            imageVector = vector,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}



