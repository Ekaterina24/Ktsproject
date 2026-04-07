package com.rykova_e.kts_project.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rykova_e.kts_project.presentation.theme.BlueColor

@Composable
fun CustomReload(
    modifier: Modifier = Modifier,
    errorText: String,
    reload: () -> Unit
) {
    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorText,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = { reload() },
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 10.dp
                ),
                colors = ButtonColors(
                    containerColor = BlueColor,
                    contentColor = Color.White,
                    disabledContainerColor = BlueColor,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    text = "Загрузить заново",
                )
            }
        }
    }
}

@Preview
@Composable
private fun CustomReloadPreview() {
    CustomReload(
        errorText = "Ошибка",
        reload = {}
    )
}