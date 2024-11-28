package com.noxinfinity.pdate.ui.view_models.chat

import com.noxinfinity.pdate.ui.view_models.base.IViewState
import io.getstream.chat.android.client.ChatClient


data class ChatState(
    val messages: List<Any> = listOf(),
    val offset: Int = 0,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val authId: String = "",
    val chatClient: ChatClient? = null,
    val isConnected: Boolean = false
) : IViewState