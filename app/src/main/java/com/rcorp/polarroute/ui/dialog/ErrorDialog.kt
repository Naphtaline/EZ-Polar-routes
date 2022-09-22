package com.rcorp.polarroute.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ErrorDialog(
    openState: Boolean,
    onDismissRequest: () -> Unit,
) {
    if (openState)
        AlertDialog(onDismissRequest = onDismissRequest,
            title = {},
            text = {
                Column(modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()) {
                    Text(
                        text = "Something went wrong please retry later",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }
            },
            buttons = {
                Column {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
                        onClick = {
                            onDismissRequest()
                        }, modifier = Modifier
                            .align(Alignment.End)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "OK",
                            style = TextStyle(color = Color.White)
                        )
                    }
                }
            }
        )
}