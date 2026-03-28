package com.rykova_e.kts_project.presentation.ui.screen.greeting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.rykova_e.kts_project.presentation.theme.BlueColor
import com.rykova_e.kts_project.presentation.theme.font_size_20
import com.rykova_e.kts_project.presentation.theme.font_size_24
import com.rykova_e.kts_project.presentation.theme.font_size_36
import com.rykova_e.kts_project.presentation.theme.padding_12
import com.rykova_e.kts_project.presentation.theme.padding_20
import com.rykova_e.kts_project.presentation.theme.padding_8
import ktsproject.composeapp.generated.resources.Res
import ktsproject.composeapp.generated.resources.app_title
import ktsproject.composeapp.generated.resources.next_step
import ktsproject.composeapp.generated.resources.welcome_text
import org.jetbrains.compose.resources.stringResource

@Composable
fun GreetingScreen(
    navigateToLoginScreen: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(padding_20),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(padding_8))
                        .sizeIn(maxWidth = 400.dp),
                    model = "https://ts2.mm.bing.net/th?id=OIP.pNJkcaxm0GdnDO9qD1jZ2gHaHa&pid=15.1",
                    contentDescription = null,
                )
                Spacer(Modifier.height(padding_12))
                Text(
                    text = stringResource(Res.string.app_title),
                    fontSize = font_size_36,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(padding_20))
                Text(
                    modifier = Modifier.padding(horizontal = padding_20),
                    text = stringResource(Res.string.welcome_text),
                    fontSize = font_size_24,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }
            Button(
                contentPadding = PaddingValues(horizontal = padding_20, vertical = padding_12),
                colors = ButtonColors(
                    containerColor = BlueColor,
                    contentColor = Color.White,
                    disabledContainerColor = BlueColor,
                    disabledContentColor = Color.White,
                ),
                onClick = { navigateToLoginScreen() }
            ) {
                Text(
                    text = stringResource(Res.string.next_step),
                    fontSize = font_size_20
                )
            }
        }
    }
}

@Preview
@Composable
private fun GreetingScreenPreview() {
    GreetingScreen(
        navigateToLoginScreen = {}
    )
}