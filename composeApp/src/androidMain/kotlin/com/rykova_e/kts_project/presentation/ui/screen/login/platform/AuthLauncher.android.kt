package com.rykova_e.kts_project.presentation.ui.screen.login.platform

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.rykova_e.kts_project.data.AndroidChannel
import com.rykova_e.kts_project.data.AndroidPlatformAuthService
import com.rykova_e.kts_project.data.repository.AuthRepositoryImpl
import com.rykova_e.kts_project.domain.use_case.AuthUseCase
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginFactory
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginViewModel
import kotlinx.coroutines.channels.Channel
import net.openid.appauth.AuthorizationService

@Composable
actual fun AuthLauncher(viewModel: LoginViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    LaunchedEffect(viewModel) {
        viewModel.openAuthPageFlow.collect { intent ->
            launcher.launch(intent)
        }
    }

    LaunchedEffect(viewModel.toastFlow) {
        viewModel.toastFlow.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
actual fun rememberLoginViewModel(): LoginViewModel {
    val context = LocalContext.current
    LoginFactory.init(
        authUseCase = AuthUseCase(AuthRepositoryImpl()),
        platformAuthService = AndroidPlatformAuthService(AuthorizationService(context.applicationContext as Application)),
        toastChannel = AndroidChannel(Channel<String>(Channel.BUFFERED)),
        openAuthPageChannel = AndroidChannel(Channel<Intent>(Channel.BUFFERED)),
        authSuccessChannel = AndroidChannel(Channel<Unit>(Channel.BUFFERED)),
        customTabsIntentProvider = { CustomTabsIntent.Builder().build() }
    )
    return remember { LoginFactory.createViewModel() }
}