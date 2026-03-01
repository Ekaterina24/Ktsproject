package com.rykova_e.kts_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.rykova_e.kts_project.navigation.NavigationApp
import com.rykova_e.kts_project.navigation.Screen
import com.rykova_e.kts_project.ui.screen.GreetingScreen
import com.rykova_e.kts_project.ui.screen.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavigationApp(
                    navController = navController,
                    greetingScreen = {
                        GreetingScreen(
                            navigateToLoginScreen = { navController.navigate(Screen.LoginScreen.route) }
                        )
                    },
                    loginScreen = {
                        LoginScreen()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}