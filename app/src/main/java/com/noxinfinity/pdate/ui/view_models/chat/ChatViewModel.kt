package com.noxinfinity.pdate.ui.view_models.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxinfinity.pdate.data.data_source.local.SharedPreferencesManager
import com.noxinfinity.pdate.data.repository.chat.ChatRepository
import com.noxinfinity.pdate.utils.helper.JWTHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferencesManager
) : ViewModel() {

    init {
        getConnection()
    }

    private val _uiState = MutableStateFlow(ChatState(isLoading = true, isConnected = false))
    val uiState: StateFlow<ChatState> = _uiState


    fun onTriggerEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.LoadMessages -> {
                loadMessages()
            }

            is ChatEvent.RemoveMessage -> {
                removeMessage(event.id)
            }

            is ChatEvent.SetLoading -> {
                _uiState.value = _uiState.value.copy(isLoading = event.isLoading)
            }
        }
    }

    private fun loadMessages() {

        _uiState.value = _uiState.value.copy(isLoading = true)

        val messages = listOf<Any>()
        _uiState.value = _uiState.value.copy(messages = messages, isLoading = false)
    }

    private fun removeMessage(id: String) {
        val messages = _uiState.value.messages.toMutableList()
        messages.removeIf { it is String && it == id }
        _uiState.value = _uiState.value.copy(messages = messages)
    }

    private fun getConnection() {

        val accessToken = sharedPreferences.getAccessToken()

        if (accessToken.isNullOrEmpty()) {
            Log.e("ChatViewModel", "Access token is null or empty.")
            return
        }

        val streamToken = JWTHelper.getStreamToken(accessToken)
        Log.d("STREAM_TOKEN", streamToken)
        val payload = JWTHelper.decodeJwtPayLoad(accessToken)
        Log.d("PAYLOAD", payload.toString())


        payload?.let {
            Log.d("CHAT_SCREEN","${it.getString("id")} connected to chat")
            ChatRepository.getClient(
                streamToken,
                it.getString("id"),
                it.getString("fullName"),
                it.getString("avatar"),
                onConnectionSuccess = {
                    Log.d("ChatViewModel", "Connected to chat")
                    viewModelScope.launch {
                        _uiState.value = _uiState.value.copy(isConnected = true, user = it)
                    }
                },
                onConnectionFailure = {
                    Log.e("ChatViewModel", "Failed to connect to chat")
                    _uiState.value = _uiState.value.copy(isConnected = false)
                }
            )
        }
    }
}
