package com.noxinfinity.pdate.ui.screens.chat.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noxinfinity.pdate.utils.heightPadding

@Composable
fun ChatList(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            horizontal = 16.dp,
        )
    ) {
        12.heightPadding()
        Text(
            text = "Message",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        10.heightPadding()
        for(i in 0..10) {
            ChatItem()
            10.heightPadding()
        }
    }
}

@Preview(name = "ChatList")
@Composable
private fun PreviewChatList() {
    ChatList()
}