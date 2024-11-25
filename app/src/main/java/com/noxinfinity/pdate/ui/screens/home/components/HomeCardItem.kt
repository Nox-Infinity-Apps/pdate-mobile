package com.noxinfinity.pdate.ui.screens.home.components


import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.data.models.home.ProfileData
import com.noxinfinity.pdate.ui.screens.common.AppButton
import com.noxinfinity.pdate.ui.screens.common.AppChip

@Composable
fun HomeCardItem(
    profileData: ProfileData,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(profileData.imageRes),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(18.dp))
        )

        // Header Section với thông tin từ ProfileData
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 6.dp
                ),
            horizontalArrangement = Arrangement.End
        ) {
            AppChip(
                text = "Here to Date",
                icon = R.drawable.ic_search_date
            )
            Spacer(modifier = Modifier.width(5.dp))
            AppChip(
                text = "1km",
                icon = R.drawable.ic_location
            )
        }

        // Description Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                // Hiển thị tên và tuổi từ ProfileData
                Text(
                    text = "${profileData.name}, ${profileData.age}",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Hiển thị mô tả từ ProfileData
                Text(
                    text = profileData.description,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}


@Composable
fun HomeCardItemButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    isChosen: Boolean? = false
) {
    AppButton(
        modifier = modifier
            .height(56.dp)
            .fillMaxSize(),
        icon = {
            Image(
                painter = painterResource(id = icon),
                modifier = Modifier.size(26.dp),
                contentDescription = null
            )
        },
        color = if (isChosen == true) Color.Red else Color.White,
        borderRadius = 8,
        onClick = onClick
    )
}

@Composable
fun HomeCardSkeletonWithShimmer(
    modifier: Modifier = Modifier
) {
    // Tạo hiệu ứng shimmer
    val shimmerColors = listOf(
        Color.Gray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.Gray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "",
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + 200f, translateAnim + 200f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background skeleton for image with shimmer
        Box(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(18.dp))
                .background(brush) // Placeholder màu xám với shimmer
        )

        // Header Section (skeleton cho chip)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp, 24.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(brush) // Shimmer cho chip đầu tiên
            )
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .size(50.dp, 24.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(brush) // Shimmer cho chip thứ hai
            )
        }

        // Description Section (skeleton cho text với shimmer)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                // Skeleton cho tên và tuổi
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(24.dp)
                        .background(brush) // Shimmer cho tên
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Skeleton cho description
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(40.dp)
                        .background(brush) // Shimmer cho mô tả
                )
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}

@Preview(name = "HomeCardItem")
@Composable
private fun PreviewHomeCardItem() {

}
