package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rykova_e.kts_project.CrashLogger
import com.rykova_e.kts_project.NetworkMonitor
import com.rykova_e.kts_project.data.source.CourseRepositoryCommonImpl
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val courseRepositoryCommon: CourseRepositoryCommonImpl
): ViewModel() {

    private val networkMonitor = NetworkMonitor()
    val isOnlineFlow = networkMonitor.isConnected

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    private val _searchFlow = MutableStateFlow("")
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            _searchFlow
                .combine(isOnlineFlow) { search, online -> search to online }
                .debounce(300L)
                .distinctUntilChanged()
                .collect { (search, online) ->
                    loadAndSearchCourses(search, online = online)
                }
        }
    }

    fun loadAndSearchCourses(search: String, page: Int = 1, online: Boolean) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            runCatching {
                val query = search.trim()
                if (query.isEmpty()) {
                    _state.update { it.copy(search = "", courses = emptyList()) }
                }
                courseRepositoryCommon.getCoursesLocalOrNetwork(
                    search = search,
                    page = page,
                    online = online
                )
            }.onSuccess { coursesWrapper ->
                _state.update {
                    it.copy(
                        courses = coursesWrapper.courses,
                        isLoading = false,
                        hasNextPage = coursesWrapper.metaData.has_next,
                        currentPage = 1,
                        isRefreshing = false
                    )
                }
            }.onFailure { error ->
                if (error is CancellationException) throw error
                _state.update { it.copy(error = "Ошибка при получении курсов", isRefreshing = false) }
                CrashLogger.logError(error)
                Napier.e("LoadCourses error", error, tag = "Network")
                _state.update {
                    it.copy(
                        isLoading = false,
                        courses = emptyList(),
                        error = error.message ?: "Unknown error",
                        isRefreshing = false
                    )
                }
            }
        }
    }

    fun loadMoreCourses() {
        if (_state.value.isLoadingMore || !_state.value.hasNextPage) return
        viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }
            val nextPage = _state.value.currentPage + 1
            runCatching {
                courseRepositoryCommon.loadMoreCourses(
                    search = _searchFlow.value,
                    nextPage = nextPage
                )
            }.onSuccess { result ->
                _state.update {
                    it.copy(
                        courses = it.courses + result.courses,
                        isLoadingMore = false,
                        hasNextPage = result.metaData.has_next,
                        currentPage = nextPage,
                    )
                }
            }.onFailure { error ->
                _state.update { it.copy(error = "Ошибка при получении курсов") }
                Napier.e("LoadMoreCourses error", error, tag = "Network")

                _state.update {
                    it.copy(
                        isLoadingMore = false,
                        courses = it.courses,
                        error = error.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun onChangedSearch(value: String) {
        _state.update { it.copy(search = value, error = null) }
        _searchFlow.value = value
    }

    fun reload() {
        if (isOnlineFlow.value) {
            _state.update { it.copy(isRefreshing = true) }
            loadAndSearchCourses(
                search = _state.value.search,
                page = _state.value.currentPage,
                online = isOnlineFlow.value
            )
        } else {
            _state.update { it.copy(isRefreshing = false) }
        }
    }
}