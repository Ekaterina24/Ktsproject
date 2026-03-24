package com.rykova_e.kts_project.presentation.ui.screen.main.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rykova_e.kts_project.presentation.theme.BlueDarkColor
import com.rykova_e.kts_project.presentation.ui.component.ParameterCountUI
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import com.rykova_e.kts_project.presentation.ui.model.UserModel

@Composable
fun HeaderCourse(
    model: CourseModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlueDarkColor)
            .padding(start = 20.dp, top = 60.dp, end = 20.dp, bottom = 20.dp)
    ) {
        Text(
            text = model.title,
            color = Color.White,
            fontSize = 44.sp,
            maxLines = 3,
            fontWeight = FontWeight.Medium,
            lineHeight = 48.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = model.description,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            maxLines = 4,
            lineHeight = 24.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier.padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                model.rating?.let { rating ->
                    ParameterCountUI(
                        value = rating,
                        imageVector = Icons.Default.Star,
                        colorText = Color.White,
                        tintIcon = Color.Yellow
                    )
                }
                model.countReviews?.let {
                    Text(
                        text = "${model.countReviews} отзывов",
                        color = Color.White,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            Text(
                text = "${model.countStudents} учащихся",
                color = Color.White,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview
@Composable
private fun HeaderCoursePreview() {
    HeaderCourse(
        model = CourseModel(
            title = "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse lobortis nibh dapibus iaculis vehicula. Nullam vel tellus blandit, tempor mi vitae, elementum neque. Vivamus efficitur laoreet malesuada. Sed rutrum urna sit amet consectetur posuere. Vivamus ac diam vitae tellus suscipit semper vel sed magna. Morbi magna lectus, cursus quis quam eget, auctor pretium urna. Morbi a blandit ex. Nullam nisl lectus, tempus ornare lorem vel, volutpat rhoncus lorem. Donec condimentum sit amet lorem ut interdum. Nulla sagittis pharetra ligula sit amet varius. Pellentesque odio felis, dignissim id dolor scelerisque, luctus lacinia mi. Integer quis fringilla sem, et accumsan dolor. Cras quis ornare urna, at condimentum dui. Ut vel tortor augue.",
            authors = listOf(UserModel(name = "author 1")),
            rating = "4",
            countStudents = 1000,
            countReviews = 5
        )
    )
}

