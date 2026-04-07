package com.rykova_e.kts_project.presentation.ui.screen.my_courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rykova_e.kts_project.presentation.ui.component.CircularProgressBarCustom
import com.rykova_e.kts_project.presentation.ui.screen.profile.UserCourseUI
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserCoursesScreen(
    modifier: Modifier,
) {
    val viewModel: UserCoursesViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    when {
        state.isLoading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressBarCustom() }

        state.courses.isNotEmpty() -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    text = "Мои курсы",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
                LazyColumn {
                    itemsIndexed(state.courses) { i, course ->
                        UserCourseUI(
                            model = course
                        )
                        if (i != state.courses.size - 1) {
                            HorizontalDivider(color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}