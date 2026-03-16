package com.rykova_e.kts_project.presentation.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rykova_e.kts_project.theme.BlueColor
import ktsproject.composeapp.generated.resources.Res
import ktsproject.composeapp.generated.resources.hide_password
import ktsproject.composeapp.generated.resources.show_password
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(
    snackbarHostState: SnackbarHostState,
    state: LoginUiState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    login: () -> Unit,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(20.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Авторизуйтесь в аккаунте",
                fontSize = 24.sp,
                fontWeight = FontWeight.W600
            )
            Spacer(Modifier.height(20.dp))
            TextField(
                value = state.username,
                onValueChange = { onUsernameChanged(it) },
                label = { Text(text = "Имя") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            Spacer(Modifier.height(10.dp))
            TextField(
                value = state.password,
                onValueChange = { onPasswordChanged(it) },
                label = { Text(text = "Пароль") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (showPassword) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.size(28.dp),
                        onClick = { showPassword = !showPassword }
                    ) {
                        Icon(
                            painter = if (showPassword)
                                painterResource(Res.drawable.show_password)
                            else
                                painterResource(Res.drawable.hide_password),
                            contentDescription = "toggle password"
                        )
                    }
                }
            )
            Spacer(Modifier.height(20.dp))
            Button(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                colors = ButtonColors(
                    containerColor = BlueColor,
                    contentColor = Color.White,
                    disabledContainerColor = BlueColor,
                    disabledContentColor = Color.White,
                ),
                onClick = { login() }
            ) {
                Text(
                    text = "Войти",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        snackbarHostState = SnackbarHostState(),
        state = LoginUiState(),
        onUsernameChanged = {},
        onPasswordChanged = {},
        login = {},
    )
}
