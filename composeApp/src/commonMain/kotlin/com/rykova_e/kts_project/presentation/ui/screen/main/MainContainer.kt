package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MainContainer(
    content: @Composable (Modifier) -> Unit,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary),
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        content(Modifier.padding(paddingValues))
    }
}