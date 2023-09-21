package com.korkalom.todolist.ui.appui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.composables.materialcolors.MaterialColors
import com.composables.materialcolors.get
import com.korkalom.todolist.utils.Routes.Companion.NAV_PREFIX
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    TopAppBar(
        title = content,
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.tertiary)
    )
}


fun doesActionRequireNavigation(action: String): Boolean {
    return action.startsWith(NAV_PREFIX)
}

//Priority Colors
val HighPrio : Color = MaterialColors.Red[400]
val MediumPrio : Color = MaterialColors.Yellow[600]
val LowPrio : Color = MaterialColors.Green[200]
val NoPrio : Color = MaterialColors.Gray[400]

const val DAY = 24 * 60 * 60 * 1000
fun getColorByPriority(priority: Int): Color {
    return when(priority){
        1 -> HighPrio
        2 -> MediumPrio
        3 -> LowPrio
        4 -> NoPrio
        else -> NoPrio
    }
}

val formatter = SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH)


const val SNACKBAR_HEIGHT: Float = 50f
const val BOTTOM_SHEET_HEIGHT: Float = 2f
@Composable
fun MySnackBar(snackbarHostState: SnackbarHostState) {
    SnackbarHost(
        modifier = Modifier.zIndex(SNACKBAR_HEIGHT),
        hostState = snackbarHostState,
        snackbar = { snackbarData: SnackbarData ->
            if (snackbarData.visuals.actionLabel.equals("error")) {
                ErrorSnackbarMessage(text = snackbarData.visuals.message)
            } else {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color.White),
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentSize()
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "")
                        Text(text = snackbarData.visuals.message)
                    }
                }
            }
        }
    )
}


@Composable
fun ErrorSnackbarMessage(text : String){
    Box(
        modifier = Modifier.zIndex(SNACKBAR_HEIGHT)
    ){
        Text(
            text = text,
            Modifier.background(MaterialTheme.colorScheme.error),
            color = MaterialTheme.colorScheme.onError,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default
        )
    }
}
