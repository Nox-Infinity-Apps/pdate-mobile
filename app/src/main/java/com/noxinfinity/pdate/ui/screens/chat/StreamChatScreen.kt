package com.noxinfinity.pdate.ui.screens.chat

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.noxinfinity.pdate.ui.view_models.chat.ChatViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.channels.SearchMode
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channels.ChannelViewModelFactory
import io.getstream.chat.android.models.querysort.QuerySortByField


@Composable
fun StreamChatScreen() {
    val chatViewModel: ChatViewModel = hiltViewModel()
    val state = chatViewModel.uiState.collectAsState().value


    ChatTheme {
        if (!state.isConnected) {
            Text("Loading")
        }else{
            ChannelsScreen(
                title = "Cuộc trò chuyện",
                searchMode = SearchMode.None,
            )
        }
    }
}

@Preview
@Composable
fun StreamChatScreenPreview() {
}