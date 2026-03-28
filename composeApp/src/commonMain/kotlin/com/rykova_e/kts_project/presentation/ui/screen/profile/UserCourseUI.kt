package com.rykova_e.kts_project.presentation.ui.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.rykova_e.kts_project.presentation.theme.GreenColor
import com.rykova_e.kts_project.presentation.ui.component.CircularProgressBarCustom
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import com.rykova_e.kts_project.presentation.ui.model.UserModel

@Composable
fun UserCourseUI(
    model: CourseModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {  }
        ,
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .size(44.dp)
                    ,
                    model = model.cover,
                    contentDescription = "image"
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = model.title,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        maxLines = 3,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 18.sp
                    )
                    Text(
                        text = model.authors.joinToString(", ") { it.name },
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        maxLines = 2,
                        lineHeight = 18.sp
                    )
                    if (model.cost != 0) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressBarCustom(
                                modifier = Modifier.size(24.dp),
                                progress = model.percentProgress?.toFloat()?.coerceIn(0f, 1f) ?: 0f,
                            )
                            Text(
                                text = "${model.score} / ${model.cost}",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                maxLines = 2,
                                lineHeight = 18.sp
                            )
                        }
                    }
                    Button(
                        modifier = Modifier.height(24.dp),
                        onClick = {},
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = GreenColor,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = GreenColor,
                        ),
                        border = BorderStroke(1.dp, GreenColor),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = "Продолжить",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
private fun UserCourseUIPreview() {
    UserCourseUI(
        model = CourseModel(
            title = "Title",
            authors = listOf(UserModel(name = "Author")),
            percentProgress = "3",
            score = 12,
            cost = 100
        )
    )
}