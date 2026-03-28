package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rykova_e.kts_project.presentation.theme.BlueColor
import com.rykova_e.kts_project.presentation.theme.padding_12
import com.rykova_e.kts_project.presentation.theme.padding_16
import com.rykova_e.kts_project.presentation.ui.component.CourseCardUI
import com.rykova_e.kts_project.presentation.ui.component.EmptyDataUI
import com.rykova_e.kts_project.presentation.ui.component.LoadingUI
import com.rykova_e.kts_project.presentation.ui.component.ReloadUI
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import ktsproject.composeapp.generated.resources.Res
import ktsproject.composeapp.generated.resources.courses_text
import ktsproject.composeapp.generated.resources.search_text
import org.jetbrains.compose.resources.stringResource

@Composable
fun CourseListScreen() {
    val viewModel = viewModel { MainViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(state.courses.size) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .distinctUntilChanged()
            .filter { items ->
                items.lastOrNull()?.index == state.courses.size - 1
            }
            .collect {
                if (state.hasNextPage && !state.isLoadingMore) {
                    viewModel.loadMoreCourses()
                }
            }
    }

    LaunchedEffect(state.search) {
        viewModel.loadAndSearchCourses(state.search)
    }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = padding_12),
                text = stringResource(Res.string.courses_text),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
            )
            TextField(
                modifier = Modifier
                    .padding(padding_12)
                    .fillMaxWidth(),
                value = state.search,
                onValueChange = { viewModel.onChangedSearch(it) },
                label = { Text(text = stringResource(Res.string.search_text)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = BlueColor,
                    focusedIndicatorColor = BlueColor,
                    focusedLabelColor = BlueColor,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                trailingIcon = {
                    if (state.search.isNotEmpty()) {
                        Icon(
                            modifier = Modifier
                                .size(padding_16)
                                .clickable {
                                    viewModel.onChangedSearch("")
                                },
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
            when {
                state.isLoading -> { LoadingUI() }
                state.courses.isEmpty() && state.error == null -> { EmptyDataUI() }

                state.error != null -> {
                    state.error?.let { error ->
                        ReloadUI(
                            error = error,
                            reload = { viewModel.reload() }
                        )
                    }
                }

                state.courses.isNotEmpty() -> {
                    LazyColumn(
                        state = listState
                    ) {
                        items(state.courses, key = { it.id }) { item ->
                            CourseCardUI(
                                model = item
                            )
                        }

                        if (state.isLoadingMore) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(padding_16),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CourseListScreenPreview() {
    CourseListScreen()
}
