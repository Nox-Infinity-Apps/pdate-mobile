package com.noxinfinity.pdate.ui.screens.home.components


import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.SuggestedUsersQuery
import com.noxinfinity.pdate.ui.common.components.NetworkImage
import com.noxinfinity.pdate.ui.screens.common.AppButton
import com.noxinfinity.pdate.ui.screens.common.AppChip

@Composable
fun HomeCardItem(
    profileData: SuggestedUsersQuery.SuggestedUser,
    openSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentIndex = rememberSaveable(profileData.fcmId) {
        mutableIntStateOf(0)
    }

    val imageList: List<String?> = if(profileData.pictures.isNullOrEmpty()) {
        listOf(
            profileData.avatarUrl ?: ""
        )
    } else {
        profileData.pictures
    }

    LaunchedEffect(profileData) {
        currentIndex.intValue = 0
    }

    Box(
        modifier = modifier.fillMaxSize().background(Color.White)
    ) {
        NetworkImage(
            url = imageList[currentIndex.intValue] ?: "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(18.dp))
                .pointerInput(profileData) {
                    detectTapGestures {
                        val boxWidth = size.width
                        if (it.x < boxWidth / 2) {
                            if (currentIndex.intValue > 0) {
                                currentIndex.intValue -= 1
                            }
                        } else {
                            if(currentIndex.intValue < imageList.size - 1) {
                                currentIndex.intValue += 1
                            }
                        }
                    }
                }
        )

        // Header Section với thông tin từ ProfileData
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 3.dp, end = 3.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                imageList.let {
                    it.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 5.dp)
                                .height(3.dp)
                                .clip(
                                    RoundedCornerShape(10.dp)
                                )
                                .background(
                                    color = if (index == currentIndex.intValue) Color.White else Color.Black,
                                )
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 10.dp,
                        vertical = 3.dp
                    ),
                horizontalArrangement = Arrangement.End
            ) {
                if (profileData.grade != null) {
                    AppChip(
                        text = profileData.grade.name ?: "",
                        icon = R.drawable.student
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                if (profileData.distance != null)
                    AppChip(
                        text = formatDistance(profileData.distance),
                        icon = R.drawable.ic_location
                    )
            }
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
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${profileData.fullName}",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = profileData.bio ?: "",
                            color = Color.White,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp
                        )
                    }

                    AppButton(
                        modifier = Modifier
                            .height(35.dp)
                            .width(35.dp)
                            .clip(CircleShape),
                        icon = {
                            Image(
                                painter = painterResource(R.drawable.expand),
                                modifier = Modifier.padding(6.dp),
                                contentDescription = null
                            )
                        },
                        onClick = openSheet
                    )
                }


                Spacer(modifier = Modifier.height(70.dp))
            }
        }


    }
}

fun formatDistance(distanceInMeters: Int): String {
    return if (distanceInMeters < 1000) {
        "Gần đây"
    } else {
        "%.1fkm".format(distanceInMeters / 1000.0)
    }
}


@Composable
fun HomeCardItemButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    isChosen: Boolean? = false,
    chosenColor: Color = Color.Red
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
        color = if (isChosen == true) chosenColor else Color.White,
        borderRadius = 12,
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
    AppButton(
        modifier = Modifier
            .height(30.dp)
            .width(30.dp)
            .clip(CircleShape),
        icon = {
            Image(
                painter = painterResource(R.drawable.expand),
                modifier = Modifier.padding(3.dp),
                contentDescription = null
            )
        },
    )
}
