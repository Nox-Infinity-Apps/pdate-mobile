package com.noxinfinity.pdate.ui.screens.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noxinfinity.pdate.utils.heightPadding

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfilePhotos(photos: List<Int>, modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Photos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        12.heightPadding()
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            userScrollEnabled = false,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.heightIn(
                max = (photos.size * 120).dp
            )
        ) {
            items(photos) { photo ->
                Image(
                    painter = painterResource(id = photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(150.dp)
                        .clip(RoundedCornerShape(18.dp))
                )
            }
        }
    }
}

