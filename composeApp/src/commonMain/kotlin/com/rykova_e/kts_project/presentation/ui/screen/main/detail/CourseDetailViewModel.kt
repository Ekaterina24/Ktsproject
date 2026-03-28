package com.rykova_e.kts_project.presentation.ui.screen.main.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rykova_e.kts_project.data.source.GetCourseRepositoryCommonImpl
import com.rykova_e.kts_project.domain.use_case.SingUpOnCourseUseCase
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseDetailViewModel(
    private val courseId: Long,
    private val getCourseRepositoryCommonImpl: GetCourseRepositoryCommonImpl,
    private val singUpOnCourseUseCase: SingUpOnCourseUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(CourseDetailState())
    val state = _state.asStateFlow()

    init {
        getCourseData()
    }

    fun getCourseData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            runCatching {
                getCourseRepositoryCommonImpl.getCourseLocalOrNetwork(courseId)
            }
                .fold(
                    onSuccess = { course ->
                        _state.update { it.copy(
                            course = course,
                            isLoading = false,
                        ) }
                    },
                    onFailure = { failed ->
                        _state.update { it.copy(
                            course = CourseModel(),
                            error = failed.message
                        ) }
                    }
                )
        }
    }

    fun onEventCourseDetail(event: OnEventCourseDetail) {
        when (event) {
            is OnEventCourseDetail.SingUpOnCourse -> {
                viewModelScope.launch {
                    runCatching {
                        singUpOnCourseUseCase.execute(event.courseId)
                    }.fold(
                        onSuccess = {
                            _state.update {
                                it.copy(
                                    toast = "Вы записаны на курс",
                                    course = it.course.copy(isRecord = true)
                                )
                            }
                        },
                        onFailure = {
                            _state.update { it.copy(error = "Ошибка записи на курс") }
                        }
                    )
                }
            }
        }
    }
}
