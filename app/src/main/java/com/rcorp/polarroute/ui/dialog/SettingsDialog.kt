package com.rcorp.polarroute.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.rcorp.polarroute.R
import com.rcorp.polarroute.data.local.AppPreferences
import org.koin.java.KoinJavaComponent.get

@Composable
fun SettingsDialog(openState: Boolean, onDismissRequest: () -> Unit) {

    val appPreferences = get<AppPreferences>(AppPreferences::class.java)

    var email by remember {
        mutableStateOf(appPreferences.username ?: "")
    }

    var password by remember {
        mutableStateOf(appPreferences.password ?: "")
    }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    if (openState)
        AlertDialog(onDismissRequest = onDismissRequest,
            title = {},
            text = {
                Column(modifier = Modifier.padding(top = 5.dp)) {
                    Text(
                        text = "Please enter your credentials",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    TextField(label = {
                        Text(text = "Email")
                    }, placeholder = {
                        Text(text = "Please enter your polar email")
                    }, value = email, onValueChange = {
                        email = it
                    }, modifier = Modifier.padding(20.dp)
                    )
                    TextField(
                        singleLine = true,
                        trailingIcon = {
                            val image = if (passwordVisible)
                                painterResource(id = R.drawable.ic_baseline_visibility_24)
                            else painterResource(id = R.drawable.ic_baseline_visibility_off_24)

                            val description =
                                if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(painter = image, description)
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        label = {
                            Text(text = "Password")
                        }, placeholder = {
                            Text(text = "Please enter your polar password")
                        }, value = password, onValueChange = {
                            password = it
                        }, modifier = Modifier.padding(20.dp)
                    )
                }
            },
            buttons = {
                Column() {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary),
                        onClick = {
                            onDismissRequest()
                            appPreferences.username = email
                            appPreferences.password = password
                        }, modifier = Modifier
                            .align(Alignment.End)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Save",
                            style = androidx.compose.ui.text.TextStyle(color = Color.White)
                        )
                    }
                }
            }
        )
}