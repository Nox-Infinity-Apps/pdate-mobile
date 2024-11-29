package com.noxinfinity.pdate.utils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.apollographql.apollo.api.DefaultUpload
import com.noxinfinity.pdate.type.Gender
import com.noxinfinity.pdate.type.Upload
import com.noxinfinity.pdate.utils.helper.UploadRequestBody
import java.io.File

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

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Gender.getString() : String {
    return when (this) {
        Gender.MALE -> "Nam"
        Gender.FEMALE -> "Nữ"
        Gender.OTHER -> "Khác"
        else -> "Không xác định"
    }
}

fun File.toUpload() : DefaultUpload {
    val body = UploadRequestBody(this, "image")
    val upload = DefaultUpload.Builder()
        .content(this.readBytes())
        .fileName(this.name)
        .contentType(body.contentType().toString())
        .build()

    return upload
}

