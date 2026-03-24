package com.rykova_e.kts_project.presentation.ui.screen.login

import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.Flow

interface PlatformChannel<T> {
    fun send(data: T): ChannelResult<Unit>
    fun receiveAsFlow(): Flow<T>
}