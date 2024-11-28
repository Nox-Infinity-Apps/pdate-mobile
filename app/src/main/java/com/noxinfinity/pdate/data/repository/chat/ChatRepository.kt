package com.noxinfinity.pdate.data.repository.chat

import com.noxinfinity.pdate.ui.view_models.chat.ChatViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import io.getstream.result.Result
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatClient: ChatClient
) {
    companion object {
        fun getClient(accessToken: String, userId: String, fullName: String, avatar: String, onConnectionSuccess: (userClient: User) -> Unit, onConnectionFailure: () -> Unit) {
            val user = User(
                id = userId,
                extraData = mutableMapOf(
                    "name" to fullName,
                    "image" to avatar
                )
            )
            val client = ChatClient.instance()
            client.connectUser(user, accessToken).enqueue { result ->
                when (result) {
                    is Result.Failure -> {
                        onConnectionFailure()
                    }
                    is Result.Success -> {
                        onConnectionSuccess(result.value.user)
                    }
                }
            }
        }
    }
}
