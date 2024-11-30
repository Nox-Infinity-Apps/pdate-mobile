package com.noxinfinity.pdate.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.HeartCrack
import com.composables.icons.lucide.Lucide
import com.noxinfinity.pdate.ui.common.components.AppIndicator
import com.noxinfinity.pdate.ui.view_models.chat.ChatViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.channels.SearchMode
import io.getstream.chat.android.compose.ui.channels.list.ChannelList
import io.getstream.chat.android.compose.ui.theme.ChatTheme


@Composable
fun StreamChatScreen(
    navController: NavController,
    rootNavController: NavController
) {
    val chatViewModel: ChatViewModel = hiltViewModel()
    val state = chatViewModel.uiState.collectAsState().value


    ChatTheme {
        if (!state.isConnected) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                AppIndicator()
            }
        }else{
            Column {
                Row(modifier = Modifier.background(ChatTheme.colors.appBackground).padding(
                    vertical = 20.dp,
                    horizontal = 20.dp
                )) {
                    Text("Cuộc trò chuyện", fontSize = 30.sp, fontWeight = FontWeight.SemiBold )
                }
                 ChannelList(modifier = Modifier.fillMaxSize(), emptyContent = {
                   Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                       Column(horizontalAlignment = Alignment.CenterHorizontally) {
                           Icon(Lucide.HeartCrack, contentDescription = "", Modifier.size(90.dp), tint = Color(0xFFBDBDBD))
                           Spacer(modifier = Modifier.size(10.dp))
                           Text("Không có cuộc trò chuyện nào", fontSize = 17.sp, color =  Color(
                               0xFF7F7777
                           )
                           )
                       }
                   }
                 })
            }
        }
    }
}

@Preview
@Composable
fun StreamChatScreenPreview() {
}