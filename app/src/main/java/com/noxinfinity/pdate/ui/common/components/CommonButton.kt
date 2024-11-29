package com.noxinfinity.pdate.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun Modifier.noRippleToggleable(
    value: Boolean,
    onValueChange: (Boolean) -> Unit
): Modifier = composed {
    toggleable(
        value = value,
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onValueChange = onValueChange
    )
}

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommonButton(
    onClick: () -> Unit,
    label: String,
    textModifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    color: Color = Color.Black,
    leftIcon: @Composable (() -> Unit)? = null,
){
    Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.background(Color.White).fillMaxWidth().padding(
        vertical = 20.dp,
        horizontal = 10.dp
    ).noRippleClickable{
        onClick()
    }.pointerInteropFilter { false }) {
        leftIcon?.invoke()
        if(leftIcon != null){
            Spacer(modifier = Modifier.padding(start = 10.dp))
        }
        Text(
            label,
            modifier = textModifier,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color
        )
    }
}