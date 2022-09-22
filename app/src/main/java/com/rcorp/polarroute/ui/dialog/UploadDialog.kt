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
fun UploadDialog(
    openState: Boolean,
    onProgressState: Boolean,
    onDismissRequest: () -> Unit,
    onUploadClick: (trackName: String) -> Unit
) {

    var trackName by remember {
        mutableStateOf("")
    }

    if (openState)
        AlertDialog(onDismissRequest = onDismissRequest,
            title = {},
            text = {
                Column(modifier = Modifier.padding(top = 5.dp).fillMaxWidth()) {
                    Text(
                        text = "Upload ...",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    if (onProgressState)
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    else
                        TextField(label = {
                            Text(text = "Track name")
                        }, placeholder = {
                            Text(text = "Please enter your track name")
                        }, value = trackName, onValueChange = {
                            trackName = it
                        }, modifier = Modifier.padding(20.dp)
                        )
                }
            },
            buttons = {
                Column {
                    if (!onProgressState)
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
                            onClick = {
                                onUploadClick(trackName)
                            }, modifier = Modifier
                                .align(Alignment.End)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Upload",
                                style = TextStyle(color = Color.White)
                            )
                        }
                }
            }
        )
}