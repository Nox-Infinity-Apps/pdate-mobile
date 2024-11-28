package com.noxinfinity.pdate.ui.view_models.chat

import com.noxinfinity.pdate.ui.view_models.base.IViewEvent

sealed class ChatEvent: IViewEvent {
    data object LoadMessages : ChatEvent()
    data class RemoveMessage(val id: String) : ChatEvent()
    data class SetLoading(val isLoading: Boolean) : ChatEvent()
}