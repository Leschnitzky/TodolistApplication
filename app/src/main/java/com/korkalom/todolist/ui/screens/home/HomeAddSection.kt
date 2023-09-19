package com.korkalom.todolist.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.korkalom.todolist.model.Task
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    viewModel: HomeScreenVM,
) {
    var title = remember { mutableStateOf(TextFieldValue("")) }
    var description = remember { mutableStateOf(TextFieldValue("")) }
    var priority = remember{ mutableStateOf<Option?>(null) }
    val scope = rememberCoroutineScope()
    PageTitleText(text = "Add Task", modifier = Modifier
        .height(60.dp)
        .fillMaxWidth())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleAndDescriptionSection(title, description)
//        DateSection()
        PrioritySection(priority)
        Button(
            onClick = {
                scope.launch {
                    async {
                        viewModel.intentChannel.trySend(
                            HomeScreenIntent.AddedNewTask(
                                Task(
                                    title = title.value.text,
                                    description = description.value.text,
                                    priority = priority.value!!.ordinal,
                                )
                            )
                        )
                        viewModel.intentChannel.trySend(
                            HomeScreenIntent.AddDismissed
                        )
                    }
                }
            }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adding a task")
                Text(text = "Add Task")
            }
        }

    }
}

@Composable
fun TitleAndDescriptionSection(title: MutableState<TextFieldValue>, description: MutableState<TextFieldValue>) {
    Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
        SectionText(text = "Add title")
        OutlinedTextField(
            shape = MaterialTheme.shapes.large,
            value = title.value,
            onValueChange = {
                title.value = it
            }
        )
        SectionText(text = "Add description")
        OutlinedTextField(
            shape = MaterialTheme.shapes.large,
            value = description.value,
            minLines = 4,
            onValueChange = {
                description.value = it
            }
        )
    }
}

@Composable
fun PrioritySection(priority: MutableState<Option?>) {
    Column(verticalArrangement = Arrangement.SpaceAround) {
        SectionText(text = "Priority")
        SelectOptionButton(priority) { priority.value = it }
    }
}

@Composable
fun PageTitleText(text : String, modifier: Modifier){
    Text(
        modifier = modifier,
        text = text,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun SectionText(text: String){
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium,
    )
}


@Composable
fun SelectOptionButton(selectedOption: MutableState<Option?>, onSelectedOptionChanged: (Option) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onSelectedOptionChanged(Option.Option1) },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            colors = if (selectedOption.value == Option.Option1) {
                ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            } else {
                ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
            }
        ) {
            Text("1")
        }

        Button(
            onClick = { onSelectedOptionChanged(Option.Option2) },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            colors = if (selectedOption.value == Option.Option2) {
                ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            } else {
                ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
            }
        ) {
            Text("2")
        }

        Button(
            onClick = { onSelectedOptionChanged(Option.Option3) },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            colors = if (selectedOption.value == Option.Option3) {
                ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            } else {
                ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
            }
        ) {
            Text("3")
        }

        Button(
            onClick = { onSelectedOptionChanged(Option.Option4) },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            colors = if (selectedOption.value == Option.Option4) {
                ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            } else {
                ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
            }
        ) {
            Text("4")
        }
    }
}

enum class Option {
    Option1,
    Option2,
    Option3,
    Option4
}