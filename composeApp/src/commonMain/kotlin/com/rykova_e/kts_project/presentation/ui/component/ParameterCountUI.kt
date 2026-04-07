package com.rykova_e.kts_project.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rykova_e.kts_project.presentation.theme.AppThemeMaterial

@Composable
fun ParameterCountUI(
    modifier: Modifier = Modifier,
    value: String,
    imageVector: ImageVector,
    colorText: Color = Color.Gray,
    tintIcon: Color? = null
) {
    Row(
        modifier = modifier.padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(12.dp),
            imageVector = imageVector,
            contentDescription = null,
            tint = tintIcon ?: LocalContentColor.current,
        )
        Text(
            text = value,
            fontSize = 12.sp,
            color = colorText
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
                tintIcon = Color.Gray
            )
        }
    }
}