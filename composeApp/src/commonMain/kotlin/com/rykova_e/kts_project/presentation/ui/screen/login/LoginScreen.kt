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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rykova_e.kts_project.presentation.theme.BlueColor
import com.rykova_e.kts_project.presentation.theme.padding_12
import com.rykova_e.kts_project.presentation.theme.padding_20
import com.rykova_e.kts_project.presentation.theme.padding_28
import com.rykova_e.kts_project.presentation.ui.navigation.Screen
import kotlinx.coroutines.launch
import ktsproject.composeapp.generated.resources.Res
import ktsproject.composeapp.generated.resources.enter_text
import ktsproject.composeapp.generated.resources.hide_password
import ktsproject.composeapp.generated.resources.label_name
import ktsproject.composeapp.generated.resources.label_password
import ktsproject.composeapp.generated.resources.login_text
import ktsproject.composeapp.generated.resources.show_password
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    navController: NavController,
) {
    val viewModel = viewModel { LoginViewModel() }
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showPassword by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                LoginUiEvent.LoginSuccessEvent -> navController.navigate(
                    Screen.MainScreen
                ) {
                    popUpTo(Screen.LoginScreen) { inclusive = true }
                }

                is LoginUiEvent.LoginErrorEvent -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = event.message)
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(padding_20)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.login_text),
                fontSize = 24.sp,
                fontWeight = FontWeight.W600
            )
            Spacer(Modifier.height(padding_20))
            TextField(
                value = state.username,
                onValueChange = { viewModel.onUsernameChanged(it) },
                label = { Text(text = stringResource(Res.string.label_name)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            Spacer(Modifier.height(padding_12))
            TextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text(text = stringResource(Res.string.label_password)) },
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
                        modifier = Modifier.size(padding_28),
                        onClick = { showPassword = !showPassword }
                    ) {
                        Icon(
                            painter = if (showPassword)
                                painterResource(Res.drawable.show_password)
                            else
                                painterResource(Res.drawable.hide_password),
                            contentDescription = "toggle password visibility"
                        )
                    }
                }
            )
            Spacer(Modifier.height(padding_20))
            Button(
                contentPadding = PaddingValues(horizontal = padding_20, vertical = padding_12),
                colors = ButtonColors(
                    containerColor = BlueColor,
                    contentColor = Color.White,
                    disabledContainerColor = BlueColor,
                    disabledContentColor = Color.White,
                ),
                onClick = { viewModel.login() }
            ) {
                Text(
                    text = stringResource(Res.string.enter_text),
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
        navController = rememberNavController(),
    )
}
