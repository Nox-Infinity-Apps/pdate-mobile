package com.noxinfinity.pdate.ui.view_models.chat

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.noxinfinity.pdate.data.data_source.local.SharedPreferencesManager
import com.noxinfinity.pdate.data.repository.chat.ChatRepository
import com.noxinfinity.pdate.ui.view_models.base.BaseViewModel
import com.noxinfinity.pdate.utils.helper.JWTHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val sharedPreferences: SharedPreferencesManager
) : BaseViewModel<ChatState, ChatEvent>() {

    override fun createInitialState(): ChatState {
        getConnection()
        return ChatState(isLoading = true, isConnected = false)
    }

    override fun onTriggerEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.LoadMessages -> {
                loadMessages()
            }
            is ChatEvent.RemoveMessage -> {
                removeMessage(event.id)
            }
            is ChatEvent.SetLoading -> {
                setState {
                    copy(isLoading = event.isLoading)
                }
            }
        }
    }

    private fun loadMessages() {
        setState {
            copy(isLoading = true)
        }
        val messages = listOf<Any>() // Replace with actual message loading logic
        setState {
            copy(messages = messages, isLoading = false)
        }
    }

    private fun removeMessage(id: String) {
        val messages = currentState.messages.toMutableList()
        messages.removeIf { it is String && it == id }
        setState {
            copy(messages = messages)
        }
    }

    private fun getConnection() {
        val accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwcm92aWRlciI6IkdPT0dMRSIsImZ1bGxOYW1lIjoiQW5oIEx1b25nIFR1YW4iLCJpZCI6ImRCV3VUME1IOWhPUWRvQnRrZTUxY3VuRjRjNzIiLCJhdmF0YXIiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NJZjZuNlo2X3QwTDBrTWNRZ1lxQ2tlYWFXdzlJek1ncEY1Y1UwUFpmUjUyeGJkZzJzPXM5Ni1jIiwiZXhwIjoxNzMyODY3Mjg0LCJzdHJlYW1Ub2tlbiI6ImV5SmhiR2NpT2lKSVV6STFOaUo5LmV5SjFjMlZ5WDJsa0lqb2laRUpYZFZRd1RVZzVhRTlSWkc5Q2RHdGxOVEZqZFc1R05HTTNNaUlzSW1saGRDSTZNVGN6TWpjNE1EZzNPU3dpYVhOeklqb2lVM1J5WldGdElFTm9ZWFFnU21GMllTQlRSRXNpTENKemRXSWlPaUpUZEhKbFlXMGdRMmhoZENCS1lYWmhJRk5FU3lKOS4zaEhncVFENWo1aFVzNVdSdHpwc20wNVVfTlB5aFBzR3BDY01nb0dGSnNFIiwiaWF0IjoxNzMyNzgwODg0LCJlbWFpbCI6ImFuaC5sdW9uZ3R1YW5AbmNjLmFzaWEifQ.zQ5OXcme5UBfcZbh8MsQCHX3Gn8iKndyeO5ROlDpIYs"

        if (accessToken.isNullOrEmpty()) {
            Log.e("ChatViewModel", "Access token is null or empty.")
            return
        }

        val streamToken = JWTHelper.getStreamToken(accessToken)
        Log.d("STREAM_TOKEN", streamToken)
        val payload = JWTHelper.decodeJwtPayLoad(accessToken)
        Log.d("PAYLOAD", payload.toString())


        payload?.let {
            ChatRepository.getClient(
                streamToken,
                it.getString("id"),
                it.getString("fullName"),
                it.getString("avatar"),
                onConnectionSuccess = {
                    Log.d("ChatViewModel", "Connected to chat")
                    setState {
                        copy(isConnected = true, user = it)
                    }
                },
                onConnectionFailure = {
                    Log.e("ChatViewModel", "Failed to connect to chat")
                    setState {
                        copy(isConnected = false)
                    }
                }
            )
        }
    }
}
