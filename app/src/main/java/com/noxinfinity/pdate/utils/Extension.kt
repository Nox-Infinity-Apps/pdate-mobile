package com.noxinfinity.pdate.utils
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun Int.heightPadding() {
    Spacer(modifier = Modifier.height(this.dp))
}

@Composable
fun Int.widthPadding() {
    Spacer(modifier = Modifier.width(this.dp))
}

@Composable
fun ImageVector.toIcon(size: Int = 14, color: Color = Color.Black ) {
    Icon(
        imageVector = this,
        contentDescription = null,
        modifier = Modifier.size(size.dp),
        tint = color
    )
}


