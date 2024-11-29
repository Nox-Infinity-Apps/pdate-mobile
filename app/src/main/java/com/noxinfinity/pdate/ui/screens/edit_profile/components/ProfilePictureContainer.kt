package com.noxinfinity.pdate.ui.screens.edit_profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.GetUserInfoQuery
import com.noxinfinity.pdate.ui.common.components.NetworkImage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Plus

@Composable
fun ProfilePictureContainer(
    picture: GetUserInfoQuery.Picture?,
    addPicture: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(150.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color = Color(0xffecebef))
            .border(width = 2.dp, color = Color(0xffdcdbdf),
                shape = RoundedCornerShape(18.dp)).clickable {
                if(picture == null) {
                    addPicture()
                } else {

                }
            },
    ) {
        if (picture != null) {
            NetworkImage(
                url = picture.url ?: ""
            )
        } else {
            Box(
                modifier = Modifier.size(35.dp)
            ) {
                Icon(
                    FontAwesomeIcons.Solid.Plus,
                    tint = Color(0xff797f87),
                    contentDescription = null,
                )
            }
        }

    }
}
