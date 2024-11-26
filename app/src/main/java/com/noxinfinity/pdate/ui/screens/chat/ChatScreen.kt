package com.noxinfinity.pdate.ui.screens.chat

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.noxinfinity.pdate.ui.screens.chat.components.ChatList
import com.noxinfinity.pdate.ui.screens.chat.components.ChatStorySection
import com.noxinfinity.pdate.ui.screens.chat.components.ChatTabBar
import com.noxinfinity.pdate.ui.screens.theme.DatingApplicationTheme
import io.getstream.chat.android.client.ChatClient
//import io.getstream.chat.android.compose.ui.channels.header.ChannelListHeader
import io.getstream.chat.android.models.User


@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    val user = User(
        id = "summer-brook-2",
        name = "Paranoid Android",
        image = "https://bit.ly/2TIt8NR",
    )
    val client = ChatClient.instance()
    val connectionState = client.clientState.connectionState

    LaunchedEffect(Unit) {
        client.connectUser(
            user = user,
            token = "fake_token"
        ).enqueue {
            result ->
            if (result.isSuccess) {
                Log.d("ChatScreen", result.toString())
            } else {
                Log.e("ChatScreen", result.errorOrNull()?.message.toString())
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item{
//            ChannelListHeader(
//                modifier = Modifier.fillMaxWidth(),
//                currentUser = user,
//                title = "Chó trí",
//                connectionState = connectionState.collectAsState().value
//            )
        }
        item {
            ChatStorySection()
        }
        item {
            ChatTabBar()
        }

        item {
            ChatList()
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    DatingApplicationTheme {
        ChatScreen()
    }
}