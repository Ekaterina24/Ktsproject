package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rykova_e.kts_project.presentation.ui.component.PostCardUI

@Composable
fun MainScreen(
    state: MainUiState,
    loadPosts: () -> Unit
) {

    LaunchedEffect(Unit) {
        loadPosts()
    }

    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                    ,
                    text = "Страница новостей",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,

                )
                LazyColumn {
                    items(state.list, key = { it.id }) { item ->
                        PostCardUI(
                            model = item
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen(
        state = MainUiState(
            list = listOf(
                PostModel(
                    id = 0L,
                    title = "First",
                    time = "10:00",
                    description = "Description 1"
                ),
                PostModel(
                    id = 1L,
                    title = "Second",
                    time = "11:00",
                    description = "Description 2"
                ),
                PostModel(
                    id = 2L,
                    title = "Third",
                    time = "12:00",
                    description = "Description 3"
                ),
            ),
        ),
        loadPosts = {}
    )
}