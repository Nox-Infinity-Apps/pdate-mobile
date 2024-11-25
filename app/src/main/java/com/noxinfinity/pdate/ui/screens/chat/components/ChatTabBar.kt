package com.noxinfinity.pdate.ui.screens.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChatTabBar(
    modifier: Modifier = Modifier,
    currentIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {},
) {
    val tabTitles = listOf("Message", "New Matched")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabTitles.forEachIndexed { index, title ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp)
                    .height(45.dp)
                    .background(
                        color = if (currentIndex == index) Color.White else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onTabSelected(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = if (currentIndex == index) Color.Black else Color.Gray,
                    fontWeight = if (currentIndex == index) FontWeight.Bold else FontWeight.Normal,
                )
            }
        }
    }
}

@Preview(name = "ChatTabBar")
@Composable
private fun PreviewChatTabBar() {
    ChatTabBar()
}