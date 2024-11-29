package com.noxinfinity.pdate.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noxinfinity.customtinderswiper.swipe_state.SwipeDirection
import com.noxinfinity.customtinderswiper.swipe_state.rememberSwipeableCardState
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.ui.view_models.home.HomeEvent
import com.noxinfinity.pdate.ui.view_models.home.HomeState
import kotlinx.coroutines.launch


@Composable
fun HomeSwipeBox(
    modifier: Modifier = Modifier,
    state: HomeState,
    onTriggerEvent: (HomeEvent) -> Unit,
) {

    val cardStackState = rememberSwipeableCardState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        HomeCardList(
            modifier = Modifier.fillMaxSize(),
            state = state,
            swipeableCardState = cardStackState,
            onTriggerEvent = onTriggerEvent
        )

        Row(
            modifier = Modifier
                .align(
                    alignment = Alignment.BottomCenter
                )
                .padding(16.dp)
                .padding(
                    horizontal = 12.dp,
                ),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomeCardItemButton(
                icon = R.drawable.ic_close,
                chosenColor = Color.Red,
                onClick = {
                        if (state.profileList.isNotEmpty() && !state.isLoading) {
                            scope.launch {
                                launch {
                                    onTriggerEvent(
                                        HomeEvent.UnLike(
                                            id = state.profileList.first()!!.fcmId
                                        )
                                    )
                                }.join()

                                cardStackState.animateTo(
                                    SwipeDirection.Left
                                )
                                onTriggerEvent(HomeEvent.PopUp)
                            }
                        }
                },
                modifier = Modifier.weight(1f),
                isChosen = cardStackState.swipedDirection == SwipeDirection.Left
            )
            HomeCardItemButton(
                icon = R.drawable.ic_loading,
                chosenColor = Color(0xffB6D0E2),
                onClick = {
                        if (state.profileList.isNotEmpty() && !state.isLoading) {
                            scope.launch {
                                cardStackState.animateTo(
                                    SwipeDirection.Up
                                )
                                onTriggerEvent(HomeEvent.PopUp)
                            }
                        }


                },
                modifier = Modifier.weight(1f),
                isChosen = cardStackState.swipedDirection == SwipeDirection.Up
            )
            HomeCardItemButton(
                icon = R.drawable.ic_love,
                chosenColor = Color(0xffFFC0CB),
                onClick = {
                    if (state.profileList.isNotEmpty() && !state.isLoading) {
                        scope.launch {
                            val callApi = launch {
                                onTriggerEvent(HomeEvent.Like(id = state.profileList.first()!!.fcmId))
                            }

                            callApi.join()

                            cardStackState.animateTo(
                                SwipeDirection.Right
                            )
                            onTriggerEvent(HomeEvent.PopUp)
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                isChosen = cardStackState.swipedDirection == SwipeDirection.Right
            )
        }
    }
}

@Preview
@Composable
fun HomeCardListPreview() {

}

