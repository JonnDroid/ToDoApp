package com.example.composeactivity2.ui.presentation.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeactivity2.data.model.ToDoEvent
import com.example.composeactivity2.ui.composable.AppBar
import com.example.composeactivity2.ui.theme.IndianGreen
import com.example.composeactivity2.ui.theme.IndianRed
import com.example.composeactivity2.ui.theme.TextBoxGrey

@Composable
fun InputScreen(inputViewModel: InputViewModel, previousScreenAction: () -> Unit) {
    val text by inputViewModel.text.collectAsState()
    val textLength by inputViewModel.textLength.collectAsState()
    val saveButtonState by inputViewModel.saveButtonState.collectAsState()

    Scaffold(topBar = { AppBar("New Note") }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            TextField(
                value = text,
                onValueChange = { newText -> inputViewModel.onTextChange(newText, newText.length) },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.90f)
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = TextBoxGrey,
                    unfocusedContainerColor = TextBoxGrey
                )
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "$textLength / 250",
                    modifier = Modifier
                        .padding(start = 10.dp, top = 3.dp)
                        .weight(1f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, top = 3.dp)
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            previousScreenAction.invoke()
                            inputViewModel.saveEvent(ToDoEvent.UpsertItem(text, 0))
                        },
                        colors = ButtonDefaults.buttonColors(IndianGreen),
                        shape = RoundedCornerShape(3.dp),
                        modifier = Modifier
                            .size(100.dp, 40.dp)
                            .padding(end = 5.dp),
                        enabled = saveButtonState
                    ) {
                        Text(text = "Save")
                    }
                    Button(
                        onClick = { previousScreenAction.invoke() },
                        colors = ButtonDefaults.buttonColors(IndianRed),
                        shape = RoundedCornerShape(3.dp),
                        modifier = Modifier.size(100.dp, 40.dp)
                    ) {
                        Text(text = "Cancel")
                    }
                }

            }
        }
    }
}
