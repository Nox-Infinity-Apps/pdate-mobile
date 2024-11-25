package com.noxinfinity.pdate.ui.screens.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.R

@Composable
fun AppChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    bgColor: Color = Color.White,
    borderRadius: Int = 16,
    borderStroke: BorderStroke? = null,
    text: String,
    @DrawableRes icon: Int
) {
    AssistChip(
        modifier = modifier,
        onClick = onClick,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = bgColor
        ),
        shape = RoundedCornerShape(borderRadius.dp),
        border = borderStroke,
        label = {
            Text(
                text = text
            )
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = icon),
                modifier = Modifier.size(16.dp),
                contentDescription = null
            )
        }
    )
}

@Preview(name = "AppChip")
@Composable
private fun PreviewAppChip() {
    AppChip(
        text = "Here to Date",
        icon = R.drawable.ic_search_date
    )
}