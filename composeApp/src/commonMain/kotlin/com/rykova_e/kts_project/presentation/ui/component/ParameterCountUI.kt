package com.rykova_e.kts_project.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.rykova_e.kts_project.presentation.theme.AppThemeMaterial
import com.rykova_e.kts_project.presentation.theme.font_size_12
import com.rykova_e.kts_project.presentation.theme.padding_12
import com.rykova_e.kts_project.presentation.theme.padding_4

@Composable
fun ParameterCountUI(
    value: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = padding_4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(padding_4)
    ) {
        Icon(
            modifier = Modifier
                .size(padding_12),
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.Gray,
        )
        Text(
            text = value,
            fontSize = font_size_12,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
private fun ParameterCountUIPreview() {
    AppThemeMaterial {
        Surface {
            ParameterCountUI(
                value = "5",
                imageVector = Icons.Outlined.Star,
            )
        }
    }
}