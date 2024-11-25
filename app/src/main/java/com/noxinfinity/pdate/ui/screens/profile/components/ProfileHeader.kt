package com.noxinfinity.pdate.ui.screens.profile.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.widthPadding
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.CheckCircle

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    name: String,
    description: String,
    isVerified: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(
                id = image
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        16.heightPadding()
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            if (isVerified) {
                4.widthPadding()
                Icon(
                    imageVector = FontAwesomeIcons.Regular.CheckCircle,
                    contentDescription = "Verified",
                    tint = Color.Blue,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        6.heightPadding()
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}