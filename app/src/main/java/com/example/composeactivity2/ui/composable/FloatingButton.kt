package com.example.composeactivity2.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composeactivity2.ui.theme.Pink80

@Composable
fun FloatingButton(nextScreenAction: () -> Unit) {
    Box(modifier = Modifier.wrapContentSize(align = Alignment.BottomEnd)) {
        FloatingActionButton(
            onClick = { nextScreenAction.invoke() },
            containerColor = Pink80
        ) {
            Icon(Icons.Filled.Add, "floating action button")
        }
    }
}