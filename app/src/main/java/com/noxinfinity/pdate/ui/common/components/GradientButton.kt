package com.noxinfinity.pdate.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GradientButton(
    title: String,
    labelColor: Color = Color(0xff0f0a09),
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Color(0xFF4CAF50), Color(0xFF81C784)),
    labelFontSize: TextUnit = 16.sp,
    textModifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(colors = colors),
                shape = RoundedCornerShape(999.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }
            .padding(vertical = 20.dp, horizontal = 30.dp)
    ) {
        Text(
            text = title,
            color = labelColor,
            textAlign = TextAlign.Center,
            modifier = textModifier,
            fontSize = labelFontSize,
            fontWeight = FontWeight.Bold
        )
    }
}
