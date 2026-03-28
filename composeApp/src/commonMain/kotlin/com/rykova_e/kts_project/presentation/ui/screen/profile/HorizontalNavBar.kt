package com.rykova_e.kts_project.presentation.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rykova_e.kts_project.presentation.theme.GreenColor

@Composable
fun HorizontalNavBar(
    list: List<String>,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(list) { i, item ->
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .width(IntrinsicSize.Max)
                    .clickable { onSelectedTab(i) }
            ) {
                Text(
                    text = item,
                    fontSize = 16.sp,
                    maxLines = 1,
                    color = if (selectedTab == i) GreenColor else MaterialTheme.colorScheme.onSurface
                )
                if (selectedTab == i) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = GreenColor,
                        thickness = 2.dp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HorizontalNavBarPreview() {
    HorizontalNavBar(
        list = listOf("Course", "My course", "Favorite"),
        selectedTab = 1,
        onSelectedTab = {}
    )
}
