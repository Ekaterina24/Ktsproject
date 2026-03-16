package com.rykova_e.kts_project.presentation.ui.screen.greeting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.rykova_e.kts_project.theme.BlueColor

@Composable
fun GreetingScreen(
    navigateToLoginScreen: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier.sizeIn(maxWidth = 400.dp),
                    model = "https://static.vecteezy.com/system/resources/previews/068/842/002/non_2x/vk-logo-icon-vk-app-transparent-background-free-png.png",
                    contentDescription = null,
                )
                Text(
                    text = "VK Profile Companion",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "Добро пожаловать!",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }
            Button(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                colors = ButtonColors(
                    containerColor = BlueColor,
                    contentColor = Color.White,
                    disabledContainerColor = BlueColor,
                    disabledContentColor = Color.White,
                ),
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
private fun GreetingScreenPreview() {
    GreetingScreen(
        navigateToLoginScreen = {}
    )
}