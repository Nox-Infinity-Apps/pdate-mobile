package com.noxinfinity.pdate.ui.screens.nearby.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.noxinfinity.pdate.navigation.Graph

@Composable
fun AvatarView(
    navController: NavController,
    userPoint: com.mapbox.geojson.Point,
    modifier: Modifier = Modifier,
    avatar: String?,
    userId: String?,
    onClick: () -> Unit = {},
    isMe: Boolean = false
) {
    ViewAnnotation(
        options = viewAnnotationOptions {
            geometry(userPoint)
        }
    ) {
        Surface(shape = CircleShape,
            modifier = modifier.size(35.dp) ) {
            Image(
                painter = rememberAsyncImagePainter(avatar ?: ""),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.border(2.dp, Color.Black)
                    .clickable(onClick = {
                        if (isMe) {
                            navController.navigate(Graph.PROFILE) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }else{
                            onClick()
                        }
                    }).fillMaxSize().clip(CircleShape)
            )
        }

    }
}