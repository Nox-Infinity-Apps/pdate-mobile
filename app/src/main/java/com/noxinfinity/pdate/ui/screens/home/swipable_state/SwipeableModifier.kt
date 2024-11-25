package com.noxinfinity.customtinderswiper.swipe_state

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

internal fun Modifier.swiper(
    state: SwipeableCardState,
    directions: Set<SwipeDirection> = setOf(
        SwipeDirection.Left,
        SwipeDirection.Right,
        SwipeDirection.Up,
    ),
    onSwiped: (SwipeDirection) -> Unit = {},
): Modifier = composed {

    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    val screenHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }

    SideEffect {
        state.directions = directions
        state.setMaxWidthAndHeight(width = screenWidth.roundToInt(), height = screenHeight.roundToInt())
    }

    this.draggableSwiper(
        state = state.swiperDraggableState,
        onDragStopped = {
            val direction = state.performFling()
            if (direction != null) onSwiped(direction)
            state.startDragAmount = Offset.Zero
        },
        onDragStarted = { offset ->
            state.startDragAmount = offset
        },
    )
        .graphicsLayer {
            val rotation = state.rotation
            val offset = state.offset

            this.rotationZ = rotation
            this.translationX = offset.x
            this.translationY = offset.y

        }
}

internal fun Modifier.draggableSwiper(
    state: SwiperDraggableState,
    onDragStarted: suspend CoroutineScope.(startedPosition: Offset) -> Unit = {},
    onDragStopped: suspend CoroutineScope.() -> Unit = {}
): Modifier = composed {
    val scope = rememberCoroutineScope()
    var velocity by remember { mutableStateOf(Offset(0f, 0f)) }

    this.pointerInput(Unit) {
        detectDragGestures(
            onDragCancel = { scope.launch { onDragStopped() } },
            onDragEnd = { scope.launch { onDragStopped() } },
            onDrag = { change, dragAmount ->
                if (change.positionChange() != Offset.Zero) change.consume()
                velocity = dragAmount
                scope.launch {
                    state.drag(MutatePriority.UserInput) { dragBy(dragAmount) }
                }
            },
            onDragStart = { dragAmount -> scope.launch { onDragStarted(dragAmount) } }
        )
    }
}