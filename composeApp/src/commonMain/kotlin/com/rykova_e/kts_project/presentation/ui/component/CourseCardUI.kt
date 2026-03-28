package com.rykova_e.kts_project.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.rykova_e.kts_project.presentation.theme.AppThemeMaterial
import com.rykova_e.kts_project.presentation.theme.font_size_14
import com.rykova_e.kts_project.presentation.theme.font_size_16
import com.rykova_e.kts_project.presentation.theme.padding_12
import com.rykova_e.kts_project.presentation.theme.padding_16
import com.rykova_e.kts_project.presentation.theme.padding_20
import com.rykova_e.kts_project.presentation.theme.padding_4
import com.rykova_e.kts_project.presentation.theme.padding_8
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import com.rykova_e.kts_project.presentation.ui.model.UserModel

@Composable
fun CourseCardUI(
    model: CourseModel,
    modifierImage: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 200.dp)
            .padding(padding_12)
            .shadow(elevation = padding_4, shape = RoundedCornerShape(padding_8))
        ,
        shape = RoundedCornerShape(padding_8),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(padding_20),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.heightIn(max = 100.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                    ,
                    verticalArrangement = Arrangement.spacedBy(padding_4)
                ) {
                    Text(
                        text = model.title,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = font_size_16,
                        maxLines = 3,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(padding_4))
                    Text(
                        text = model.authors.joinToString(", ") { it.name },
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = font_size_14,
                        fontWeight = FontWeight.Light,
                        maxLines = 2,
                        lineHeight = 16.sp
                    )
                }
                Spacer(Modifier.width(padding_8))
                Box {
                    AsyncImage(
                        modifier = modifierImage
                            .clip(RoundedCornerShape(padding_4))
                            .size(60.dp)
                        ,
                        model = model.cover,
                        contentDescription = "image"
                    )
                    Box(
                        Modifier
                            .offset(padding_4, -padding_4)
                            .background(Color.LightGray, CircleShape)
                            .padding(padding_4)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(padding_16),
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Column {
                Row {
                    ParameterCountUI(
                        value = model.rating,
                        imageVector = Icons.Outlined.Star
                    )
                    ParameterCountUI(
                        value = model.countStudents.toString(),
                        imageVector = Icons.Default.Person
                    )
                    model.duration?.let { duration ->
                        ParameterCountUI(
                            value = duration.toString(),
                            imageVector = Icons.Default.AccountCircle
                        )
                    }
                    Icon(
                        modifier = Modifier
                            .padding(start = padding_4)
                            .align(Alignment.CenterVertically)
                            .size(padding_12),
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = Color.Gray,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CourseCardUIPreview() {
    AppThemeMaterial {
        Surface {
            CourseCardUI(
                modifierImage = Modifier
                    .clip(RoundedCornerShape(padding_4))
                    .background(Color.Gray)
                    .size(60.dp)
                ,
                model = CourseModel(
                    id = 0L,
                    title = "Title",
                    description = "Description",
                    authors = listOf(
                        UserModel(name = "Author 1"), UserModel(name = "Author 2")
                    ),
                    rating = "5"
                )
            )
        }
    }
}
