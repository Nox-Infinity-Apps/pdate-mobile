package com.noxinfinity.pdate.ui.screens.chat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noxinfinity.pdate.R

import com.noxinfinity.pdate.utils.heightPadding

@Composable
fun ChatStoryItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = "John Doe from rule 34",
    image: @Composable () -> Unit = {
        Image(
            painter = painterResource(id = R.drawable.default_avatar),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(80.dp)
    ) {
        Box(
            modifier = modifier
                .size(70.dp)
                .clip(CircleShape)
                .clickable { onClick() }
        ) {
            image()
        }
        6.heightPadding()
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(name = "StoryItem")
@Composable
private fun PreviewStoryItem() {
    ChatStoryItem()
}