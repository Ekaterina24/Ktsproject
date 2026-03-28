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
import com.rykova_e.kts_project.presentation.theme.BlueColor
import com.rykova_e.kts_project.presentation.theme.padding_12
import com.rykova_e.kts_project.presentation.theme.padding_20
import com.rykova_e.kts_project.presentation.theme.padding_4
import ktsproject.composeapp.generated.resources.Res
import ktsproject.composeapp.generated.resources.reload_text
import org.jetbrains.compose.resources.stringResource

@Composable
fun ReloadUI(
    error: String,
    reload: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding_12),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(padding_4))
            Button(
                onClick = reload,
                contentPadding = PaddingValues(
                    horizontal = padding_20,
                    vertical = padding_12
                ),
                colors = ButtonColors(
                    containerColor = BlueColor,
                    contentColor = Color.White,
                    disabledContainerColor = BlueColor,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.reload_text),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ReloadUIPreview() {
    ReloadUI(
        error = "Error message",
        reload = {}
    )
}