package com.rykova_e.kts_project.presentation.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rykova_e.kts_project.presentation.theme.BlueColor
import com.rykova_e.kts_project.presentation.ui.screen.login.event.LoginStateEvent
import com.rykova_e.kts_project.presentation.ui.screen.login.platform.AuthLauncher
import com.rykova_e.kts_project.presentation.ui.screen.login.platform.LoginEventsHandler
import com.rykova_e.kts_project.presentation.ui.screen.login.platform.rememberLoginViewModel

@Composable
fun LoginScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    val viewModel: LoginViewModel = rememberLoginViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginEventsHandler(viewModel, snackbarHostState, navController)
    AuthLauncher(viewModel)

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
            if (state.isLoading) CircularProgressIndicator()
            Text(
                text = "Авторизуйтесь в аккаунте",
                fontSize = 24.sp,
                fontWeight = FontWeight.W600
            )
            Spacer(Modifier.height(20.dp))
            if (!state.isShowInputCode) {
                Button(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                    colors = ButtonColors(
                        containerColor = BlueColor,
                        contentColor = Color.White,
                        disabledContainerColor = BlueColor,
                        disabledContentColor = Color.White,
                    ),
                    onClick = { viewModel.onLoginStateEvent(LoginStateEvent.OnOpenLoginPage) }
                ) {
                    Text(
                        text = "Войти через Stepik",
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            if (state.isShowInputCode) {
                TextField(
                    value = state.code,
                    onValueChange = { viewModel.onLoginStateEvent(LoginStateEvent.OnChangedCode(it)) },
                    label = { Text(text = "Вставьте код") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
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
                    onClick = { viewModel.onLoginStateEvent(LoginStateEvent.OnAuthByCode) }
                ) {
                    Text(
                        text = "Авторизаваться",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        snackbarHostState = SnackbarHostState(),
        navController = rememberNavController()
    )
}
