package com.noxinfinity.pdate.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.R

@Composable
fun AppVerifiedIcon(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_verified),
        contentDescription = null,
        modifier = modifier.size(16.dp)
    )
}