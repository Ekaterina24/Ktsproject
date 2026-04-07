package com.rykova_e.kts_project.presentation.ui.screen.main.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rykova_e.kts_project.presentation.theme.BlueColor
import com.rykova_e.kts_project.presentation.theme.GreenColor
import com.rykova_e.kts_project.presentation.ui.component.CircularProgressBarCustom
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CourseDetailScreen(
    modifier: Modifier = Modifier,
    courseId: Long,
    snackbarHostState: SnackbarHostState,
) {
    val viewModel: CourseDetailViewModel = koinViewModel {
        parametersOf(courseId)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.toast) {
        state.toast?.let { toast ->
            scope.launch {
                snackbarHostState.showSnackbar(toast)
            }
        }
    }

    when {
        state.isLoading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressBarCustom() }

        state.error != null -> TODO("show error")
        else -> {
            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    item {
                        HeaderCourse(state.course)
                    }
                }

                if (!state.course.isPaid) {
                    Button(
                        modifier = modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        onClick = {
                            if (!state.course.isRecord) {
                                viewModel.onEventCourseDetail(
                                    OnEventCourseDetail.SingUpOnCourse(
                                        state.course.id
                                    )
                                )
                            }
                        },
                        contentPadding = PaddingValues(
                            horizontal = 20.dp,
                            vertical = 10.dp
                        ),
                        colors = ButtonColors(
                            containerColor = if (state.course.isRecord) GreenColor else BlueColor,
                            contentColor = Color.White,
                            disabledContainerColor = if (state.course.isRecord) GreenColor else BlueColor,
                            disabledContentColor = Color.White,
                        ),
                    ) {
                        Text(
                            text = if (state.course.isRecord) "Продолжить" else "Записаться",
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CourseDetailScreenPreview() {
    CourseDetailScreen(
        courseId = 0,
        snackbarHostState = SnackbarHostState(),
    )
}
