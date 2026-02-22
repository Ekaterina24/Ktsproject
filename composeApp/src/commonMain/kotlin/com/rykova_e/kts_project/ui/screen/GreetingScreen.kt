package com.rykova_e.kts_project.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun GreetingScreen(
    navigateToLoginScreen: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier.sizeIn(maxWidth = 400.dp),
                    model = "https://avatars.mds.yandex.net/i?id=d5417aa4259649bad10567f6c5514288_l-5358581-images-thumbs&n=13",
                    contentDescription = null,
                )
                Text(
                    text = "Салон красоты",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "Добро пожаловать в салон красоты!",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }
            Button(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                onClick = { navigateToLoginScreen() }
            ) {
                Text(
                    text = "Далее",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun GreetingScreenPreview() {
    GreetingScreen(
        navigateToLoginScreen = {}
    )
}