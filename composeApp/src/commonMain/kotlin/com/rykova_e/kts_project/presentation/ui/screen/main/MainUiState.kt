package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.compose.runtime.Immutable
import com.rykova_e.kts_project.presentation.ui.model.CourseModel

@Immutable
data class MainUiState(
    val courses: List<CourseModel> = listOf(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasNextPage: Boolean = true,
    val currentPage: Int = 1,
    val search: String = "",
    val isRefreshing: Boolean = false
)
