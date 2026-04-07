package com.rykova_e.kts_project.presentation.ui.screen.login.platform

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginViewModelCommon

@Composable
actual fun AuthLauncher(viewModel: LoginViewModelCommon) {
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