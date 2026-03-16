package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.compose.runtime.Immutable

@Immutable
data class MainUiState(
    val list: List<PostModel> = listOf()
)
