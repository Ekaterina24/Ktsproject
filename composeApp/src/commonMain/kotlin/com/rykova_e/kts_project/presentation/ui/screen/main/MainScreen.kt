package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.rykova_e.kts_project.presentation.theme.BlueColor
import com.rykova_e.kts_project.presentation.ui.component.CircularProgressBarCustom
import com.rykova_e.kts_project.presentation.ui.component.CourseCardUI
import com.rykova_e.kts_project.presentation.ui.component.CustomReload
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun CourseListScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = viewModel { MainViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()
    val refreshState = rememberPullToRefreshState()

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
        Surface(modifier = modifier) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    text = "Курсы",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
                TextField(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    value = state.search,
                    onValueChange = { viewModel.onChangedSearch(it) },
                    label = { Text(text = "Поиск") },
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
                                    .size(16.dp)
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
                PullToRefreshBox(
                    isRefreshing = state.isRefreshing,
                    state = refreshState,
                    onRefresh = { viewModel.reload() }
                ) {
                    if (state.isLoading) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressBarCustom(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(50.dp)
                                    .fillMaxSize()
                            )
                        }
                    } else {
                        if (state.courses.isEmpty() && state.error == null) {
                            Box {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .size(150.dp),
                                        model = "https://yandex-images.clstorage.net/5R0IxP184/3fac13ZlPBz/z2-1IdmeB3-mC8RBZ4hXdCpP00gBLmgoU5KBlBMNORfXUQZq6dKDri2HA-IBa2MRlonWIbm6IRLIeuosnpwx84l5bHLKj9T5Ys0BXSsBG-8jxFxDSaIwLZKUE1SUW67U2NrUJrYAHHA9W1ZkA6UpkwdNHiyxtSCJ3_NjZVAdc_IDNGexmFIJMyDOThpmzNqrrwoUQUWqNqXT1pbcEIuokRRXiqx5TRd4eZhcYUxtLt7ISt_ofTUuStSzKKsX1PE_iXjieNuRTj-oDEQZ6QoeKSkEkAJJqv4iE13X1JKJ6BUbnY_ncYicOLyY0OzL52GWCcDfZLz5ZE7SdGxsVhV0OUUzZ_Sa38ex54vJm2YNUuGhipuNQiq5KVif1B_bBKmd3ByAL_eDGrA0Vl1pQyejVUbClux6ualF3nSrINLa-3XIseO52lFH-2TLClSoANYhqgpUzcorsGFeFlDeV4ZkF9-fx-a5gJe381RSqs1qYl3ORh6qNrDsihY8o2cem7e2TnwisdJfzHavy8aebknSKiHJHshD7vIrHtNfFBhJKFoSX4-jvE0dPrgSXiGD7-9dBQ8VKHX5IAvSeyzhGBO5ewa8oz8VkUy5bM0JFKmMW28uw5KPhmx671-RkJbQyaMfH1TALLZMm3Mx0hJpS6spXs_GlqXyuGmK0PnmKZHb8bGGvC32FRvE_-hDCNPtRd4nJY3SxYSh8m8alRfYXk4jXB4RSebwAh84_lpca4mradpDx1pgu7AlzJE6JCAf13R2TzKod93YhjFtTUFeLArd6aeJ3AdJrXEm3JVSlFsN5hwUUAEsOwscNvAc1GqJp-OfQEoWprOzrsEVO-Rn2ds4_09yZbySEQRwZ8WI3qAL0GYmwpVHhmx4YdFdWhnezizX25dJ4_PPVT4-XhZoSihtU47DWa8-dGPK2X7n7xnS-D7Ns6670lbLMyAMQ1EmyBCjbgIRRYgrf6ZRnl7RX8",
                                        contentDescription = "image"
                                    )
                                    Text(
                                        text = "Список данных пуст",
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                        if (state.error != null) {
                            state.error?.let { error ->
                                CustomReload(
                                    errorText = error,
                                    reload = viewModel::reload
                                )
                            }
                        }
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
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressBarCustom()
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