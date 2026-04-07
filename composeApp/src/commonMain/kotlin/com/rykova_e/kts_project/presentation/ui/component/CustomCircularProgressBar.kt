package com.rykova_e.kts_project.presentation.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rykova_e.kts_project.presentation.theme.BlueColor

@Composable
fun CircularProgressBarCustom(
    modifier: Modifier = Modifier,
    progress: Float? = null
) {
    if (progress == null) {
        CircularProgressIndicator(
            modifier = modifier.size(50.dp),
            color = BlueColor,
            strokeWidth = 5.dp,
        )
    } else {
        CircularProgressIndicator(
            modifier = modifier.size(50.dp),
            color = BlueColor,
            strokeWidth = 5.dp,
            progress = { progress }
        )
    }
}