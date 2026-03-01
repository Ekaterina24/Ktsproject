package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.lifecycle.ViewModel
import com.rykova_e.kts_project.domain.repository.PostRepository
import com.rykova_e.kts_project.presentation.ui.mapper.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {

    private val postRepository = PostRepository()

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()


    fun loadPosts() {
        val posts = postRepository.getList()
        _state.value = _state.value.copy(list = posts.map { it.toUI() })
    }
}