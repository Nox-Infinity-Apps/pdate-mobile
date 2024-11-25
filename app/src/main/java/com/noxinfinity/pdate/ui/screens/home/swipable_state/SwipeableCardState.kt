package com.noxinfinity.customtinderswiper.swipe_state

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animate
import androidx.compose.foundation.MutatePriority
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt


public enum class SwipeDirection {
    Left,
    Right,
    Up,
    Down,
}

@Composable
fun rememberSwipeableCardState() : SwipeableCardState {
    return remember {
        SwipeableCardState()
    }
}

class SwipeableCardState(
    private val animationSpec: AnimationSpec<Offset> = SpringSpec(),
    private val horizontalThreshold: (Float) -> Float = { distance -> distance * 0.4f },
    private val verticalThreshold: (Float) -> Float = { distance -> distance * 0.3f },
) {
    private var maxHeight: Int by mutableIntStateOf(0)

    private var maxWidth: Int by mutableIntStateOf(0)

    public var offset: Offset by mutableStateOf(Offset.Zero)
        private set

    public var scale: Float by mutableFloatStateOf(0.0f)
        private set

    public var rotation: Float by mutableFloatStateOf(0.0f)
        private set

    public var swipedDirection: SwipeDirection? by mutableStateOf(null)
        private set

    internal var startDragAmount by mutableStateOf(Offset.Zero)

    internal val swiperDraggableState = swiperDraggableState { delta ->

        offset += delta
        scale = normalize(
            min = 0.0f,
            max = maxWidth.toFloat() / 2,
            value = sqrt(offset.x.pow(2) + offset.y.pow(2)),
            startRange = 0.6f,
        )
        rotation = computeRotation(startDragAmount, offset)

        swipedDirection = computeTarget(offset)

    }

    public var isAnimationRunning: Boolean by mutableStateOf(false)
        private set

    public var isEnabled: Boolean by mutableStateOf(true)

    internal var directions: Set<SwipeDirection> by mutableStateOf(setOf())

    //Real Function
    public suspend fun animateToCenter(
        animation: AnimationSpec<Offset> = animationSpec,
    ) {
        try {
            internalAnimateTo(Offset.Zero, animation)
        } catch (ex: CancellationException) {
            internalSnapTo(Offset.Zero)
        }
    }

    private fun resetState() {
        offset = Offset.Zero
        scale = 0.0f
        rotation = 0.0f
        swipedDirection = null
    }

    public suspend fun animateTo(
        target: SwipeDirection,
        animation: AnimationSpec<Offset> = animationSpec,
    ) {
        try {
            animateToDirection(target, animation)
        } catch (ex: CancellationException) {
            snapToDirection(target)
        } finally {
            resetState()
        }
    }

    public suspend fun snapTo(target: Offset) {
        swiperDraggableState.drag {
            dragBy(target - offset)
        }
    }

    internal suspend fun performFling(): SwipeDirection? {
        val target = computeTarget(offset)
        val realTarget = target
            ?.takeIf { it in directions }
            ?.takeIf { isEnabled }
        if (realTarget != null) {
            try {
                animateToDirection(target)
            } catch (ex: CancellationException) {
                snapToDirection(target)
            }
        } else {
            internalAnimateTo(Offset.Zero, animationSpec)
        }
        return realTarget
    }


    //Utils Function
    private suspend fun internalAnimateTo(target: Offset, animationSpec: AnimationSpec<Offset>) {
        swiperDraggableState.drag {
            try {
                var prevValue = offset
                isAnimationRunning = true
                animate(
                    typeConverter = Offset.VectorConverter,
                    initialValue = offset,
                    targetValue = target,
                    animationSpec = animationSpec
                ) { value, _ ->
                    dragBy(value - prevValue)
                    prevValue = value
                }
            } finally {
                isAnimationRunning = false
            }
        }
    }

    private suspend fun internalSnapTo(
        target: Offset,
        dragPriority: MutatePriority = MutatePriority.Default
    ) {
        swiperDraggableState.drag(dragPriority) {
            dragBy(target - offset)
        }
    }

    private fun normalize(
        min: Float,
        max: Float,
        value: Float,
        startRange: Float = 0f,
        endRange: Float = 1f
    ): Float {
        require(startRange < endRange) {
            "startRange must be less than endRange."
        }
        val coercedValue = value.coerceIn(min, max)
        return (coercedValue - min) / (max - min) * (endRange - startRange) + startRange
    }

    private suspend fun animateToDirection(
        target: SwipeDirection,
        animation: AnimationSpec<Offset> = animationSpec,
    ) {
        val targetOffset = offsetByDirection(target)
        internalAnimateTo(targetOffset, animation)
    }

    internal fun offsetByDirection(direction: SwipeDirection): Offset = when (direction) {
        SwipeDirection.Left -> {
            val distance = -maxWidth - horizontalThreshold(maxWidth.toFloat() * 1.2f)
            Offset(distance, offset.y)
        }

        SwipeDirection.Right -> {
            val distance = maxWidth + horizontalThreshold(maxWidth.toFloat()* 1.2f)
            Offset(distance, offset.y)
        }

        SwipeDirection.Up -> {
            val distance = -maxHeight - verticalThreshold(maxHeight.toFloat())
            Offset(offset.x, distance)
        }

        SwipeDirection.Down -> {
            val distance = maxHeight + verticalThreshold(maxHeight.toFloat())
            Offset(offset.x, distance)
        }
    }

    private suspend fun snapToDirection(target: SwipeDirection) {
        val targetOffset = offsetByDirection(target)
        internalSnapTo(targetOffset, MutatePriority.PreventUserInput)
    }

    private fun computeRotation(
        startDragPosition: Offset,
        offset: Offset
    ): Float {
        val targetRotation = normalize(
            min = 0.0f,
            max = maxWidth.toFloat(),
            value = abs(offset.x),
            startRange = 0f,
            endRange = 30f
        )

        val sign = if (startDragPosition.y < maxHeight.toFloat() / 2) {
            offset.x.sign
        } else {
            -offset.x.sign
        }
        return targetRotation * sign
    }

    private fun computeTarget(offset: Offset): SwipeDirection? {
        val horizontalRelativeThreshold = abs(horizontalThreshold(maxWidth.toFloat()))
        val verticalRelativeThreshold = abs(verticalThreshold(maxWidth.toFloat()))

        return when {
            offset.x <= 0f && abs(offset.x) > horizontalRelativeThreshold -> SwipeDirection.Left
            offset.x >= 0f && offset.x > horizontalRelativeThreshold -> SwipeDirection.Right
            offset.y <= 0f && abs(offset.y) > verticalRelativeThreshold -> SwipeDirection.Up
            offset.y >= 0f && offset.y > verticalRelativeThreshold -> SwipeDirection.Down
            else -> null
        }
    }

    internal fun setMaxWidthAndHeight(height: Int, width: Int) {
        maxHeight = height
        maxWidth = width
    }
}




