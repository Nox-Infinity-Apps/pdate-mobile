package com.noxinfinity.pdate.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GradientButton(title: String, labelColor: Color = Color(0xff0f0a09), modifier: Modifier = Modifier, colors: List<Color> = listOf(Color(0xFF4CAF50), Color(0xFF81C784)), labelFontSize: TextUnit = 16.sp, textModifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = colors
                ),
                shape = RoundedCornerShape(999.dp)
            ).padding(vertical = 5.dp, horizontal = 24.dp),
        interactionSource = remember { MutableInteractionSource() },
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Text(
            text = title,
            color = labelColor,
            textAlign = TextAlign.Center,
            modifier = textModifier,
            fontSize = labelFontSize
        )
    }
}