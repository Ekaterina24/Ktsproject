package com.rykova_e.kts_project.presentation.ui.mapper

import okio.IOException

fun Throwable.getErrorMessage(): String {
    return when (this) {
        is IllegalArgumentException -> this.message ?: ""
        is IOException -> "Отсутствует интернет-подключение"
        else -> "Неизвестная ошибка"
    }
}