package com.noxinfinity.pdate.ui.screens.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.noxinfinity.pdate.ui.view_models.chat.ChatEvent
import com.noxinfinity.pdate.ui.view_models.chat.ChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun StreamChatScreen() {
    val chatViewModel: ChatViewModel = hiltViewModel()
    val state = chatViewModel.uiState.collectAsState().value

    Column(){
        Text("Stream Chat")
        Text("Is loading ${state.isLoading}")
        Button(onClick = {
            chatViewModel.onTriggerEvent(ChatEvent.SetLoading(false))
        }) {
            Text("Load Messages")
        }
    }
}

@Preview
@Composable
fun StreamChatScreenPreview() {
}