package com.rykova_e.kts_project.presentation.ui.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rykova_e.kts_project.presentation.ui.component.CustomLoader
import com.rykova_e.kts_project.presentation.ui.component.CustomReload
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit
) {
    val viewModel: UserProfileViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getProfileData()
    }

    Surface(modifier = modifier) {
        when {
            state.isLoading -> {
                CustomLoader()
            }
            state.error != null -> {
                state.error?.let { error ->
                    CustomReload(
                        errorText = error,
                        reload = viewModel::reload
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.heightIn(max = 100.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .size(60.dp),
                            model = ImageRequest.Builder(LocalPlatformContext.current)
                                .data(state.user.avatar)
                                .crossfade(true)
                                .build(),
                            contentDescription = "image"
                        )
                        Spacer(Modifier.width(20.dp))
                        Text(
                            text = "${state.user.name} ${state.user.surname}",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp,
                            maxLines = 3,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            modifier = Modifier
                                .size(36.dp)
                                .clickable {
                                    viewModel.logout()
                                    navigateToLogin()
                                },
                            imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserProfileScreenPreview() {
    UserProfileScreen(
        navigateToLogin = {}
    )
}