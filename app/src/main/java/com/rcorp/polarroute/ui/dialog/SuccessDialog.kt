package com.rcorp.polarroute.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.rcorp.polarroute.R

@Composable
fun SuccessDialog(
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
                    Text(text = "Success", style = MaterialTheme.typography.titleLarge.copy(color = Color.Black))
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