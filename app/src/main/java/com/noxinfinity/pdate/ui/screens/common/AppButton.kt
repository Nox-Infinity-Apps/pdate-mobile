package com.noxinfinity.pdate.ui.screens.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.utils.toIcon
import com.noxinfinity.pdate.utils.widthPadding
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.Bell

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    text: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {},
    borderStroke: BorderStroke = BorderStroke(0.dp, MaterialTheme.colorScheme.primary),
    borderRadius: Int = 0,
    horizontalPadding: Int = 0,
    verticalPadding: Int = 0,
    color: Color = Color.White
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .border(
                border = borderStroke,
                shape = RoundedCornerShape(borderRadius.dp)
            )
            .padding(
                horizontal = horizontalPadding.dp,
                vertical = verticalPadding.dp
            )
            .background(
                color = color,
                shape = RoundedCornerShape(borderRadius.dp)
            ), contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier,
        ) {
            icon?.let {
                it()
            }
            if (icon != null && text != null) {
                4.widthPadding()
            }
            text?.let {
                it()
            }
        }
    }
}

@Preview
@Composable
fun AppButtonPreview() {
    AppButton(
        text = {
            Text(
                text = "Button",
                style = TextStyle(
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            )
        },
        icon = {
            FontAwesomeIcons.Regular.Bell.toIcon(14, Color.White)
        },
        onClick = {},
        borderStroke = BorderStroke(1.dp, Color.Blue),
        borderRadius = 50
    )
}