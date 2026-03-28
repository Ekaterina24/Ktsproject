package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rykova_e.kts_project.data.source.remote.CourseRepositoryCommonImpl
import com.rykova_e.kts_project.presentation.ui.mapper.getErrorMessage
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MainViewModel: ViewModel() {
    private val courseRepositoryCommonImpl = CourseRepositoryCommonImpl()

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()
    private var searchJob: Job? = null

    fun loadAndSearchCourses(search: String = "", page: Int = 1) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            if (search.isEmpty()) {
                _state.update { it.copy(search = "", courses = emptyList()) }
            } else {
                _state.update { it.copy(search = search) }
                delay(300L)
            }
            courseRepositoryCommonImpl.getCoursesNetwork(
                search = search,
                page = page
            ).fold(
                onSuccess = { result ->
                    _state.update {
                        it.copy(
                            courses = result.courses,
                            isLoading = false,
                            hasNextPage = result.hasNext,
                            currentPage = 1,
                        )
                    }
                },
                onFailure = { e ->
                    if (e is CancellationException) throw e
                    _state.update {
                        it.copy(
                            isLoading = false,
                            courses = emptyList(),
                            error = e.getErrorMessage()
                        )
                    }
                    Napier.e(message = "LoadCourses error", throwable = e, tag = "Network")
                }
            )
        }
    }

    fun loadMoreCourses() {
        if (_state.value.isLoadingMore || !_state.value.hasNextPage) return
        viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }
            val nextPage = _state.value.currentPage + 1

            courseRepositoryCommonImpl.loadMoreCourses(
                search = _state.value.search,
                nextPage = nextPage
            ).fold(
                onSuccess = { result ->
                    _state.update {
                        it.copy(
                            courses = it.courses + result.courses,
                            isLoadingMore = false,
                            hasNextPage = result.hasNext,
                            currentPage = nextPage,
                        )
                    }

                },
                onFailure = { e ->
                    _state.update {
                        it.copy(
                            isLoadingMore = false,
                            courses = it.courses,
                            error = e.getErrorMessage()
                        )
                    }
                    Napier.e(message = "LoadMoreCourses error", throwable = e, tag = "Network")
                }
            )
        }
    }

    fun onChangedSearch(value: String) {
        _state.update { it.copy(search = value, error = null) }
    }

    fun reload() {
        loadAndSearchCourses(_state.value.search)
    }
}