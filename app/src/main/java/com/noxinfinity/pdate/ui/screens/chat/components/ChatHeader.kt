package com.noxinfinity.pdate.ui.screens.chat.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHeader(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text("Chat",  fontWeight = FontWeight.Bold)
        },
        actions = {
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                )
            }
        }
    )
}

@Preview(name = "ChatHeader")
@Composable
private fun PreviewChatHeader() {
    ChatHeader()
}