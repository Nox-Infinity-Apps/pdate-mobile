package com.noxinfinity.pdate.ui.screens.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.utils.widthPadding

@Composable
fun ChatStorySection(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            12.widthPadding()
        }
        items(10) {
            ChatStoryItem()
        }
    }
}

@Preview(name = "StorySection")
@Composable
private fun PreviewStorySection() {
    ChatStorySection()
}