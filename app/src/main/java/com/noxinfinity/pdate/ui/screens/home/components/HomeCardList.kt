package com.noxinfinity.pdate.ui.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.noxinfinity.customtinderswiper.swipe_state.SwipeDirection
import com.noxinfinity.customtinderswiper.swipe_state.SwipeableCardState
import com.noxinfinity.customtinderswiper.swipe_state.swiper
import com.noxinfinity.pdate.ui.view_models.home.HomeEvent
import com.noxinfinity.pdate.data.models.home.ProfileData
import kotlinx.coroutines.launch

@Composable
fun HomeCardList(
    modifier: Modifier = Modifier,
    items: List<ProfileData>,
    isLoading: Boolean,
    swipeableCardState: SwipeableCardState,
    onTriggerEvent: (HomeEvent) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ){
        val scope = rememberCoroutineScope()

        if(isLoading){
            HomeCardSkeletonWithShimmer()
        }
        if(items.size == 1) {
            HomeCardItem(
                profileData = items.first(),
                modifier = Modifier.swiper(
                    state = swipeableCardState,
                    onSwiped = {
                        when(it){
                            SwipeDirection.Left -> {
                                onTriggerEvent(HomeEvent.DislikeProfile)
                            }
                            SwipeDirection.Right -> {
                                onTriggerEvent(HomeEvent.LikeProfile)

                            }
                            SwipeDirection.Up -> {
                                onTriggerEvent(HomeEvent.ThinkProfile)
                            }
                            SwipeDirection.Down -> {}
                        }
                        scope.launch {
                            swipeableCardState.snapTo(Offset.Zero)
                        }
                    }
                )
            )
        } else if(items.size >= 2) {
            items.take(2).reversed().forEachIndexed{
                    index, item  ->
                if(index == 1){
                    HomeCardItem(
                        profileData = item,
                        modifier = Modifier.swiper(
                            state = swipeableCardState,
                            onSwiped = {
                                when(it){
                                    SwipeDirection.Left -> {
                                        onTriggerEvent(HomeEvent.DislikeProfile)
                                    }
                                    SwipeDirection.Right -> {
                                        onTriggerEvent(HomeEvent.LikeProfile)

                                    }
                                    SwipeDirection.Up -> {
                                        onTriggerEvent(HomeEvent.ThinkProfile)
                                    }
                                    SwipeDirection.Down -> {}
                                }
                                scope.launch {
                                    swipeableCardState.snapTo(Offset.Zero)
                                }
                            }
                        )
                    )
                } else {
                    HomeCardItem(
                        profileData = item,
                        modifier = Modifier.graphicsLayer {
                            scaleX = swipeableCardState.scale
                            scaleY = swipeableCardState.scale
                        }
                    )
                }


            }
        }
    }
}