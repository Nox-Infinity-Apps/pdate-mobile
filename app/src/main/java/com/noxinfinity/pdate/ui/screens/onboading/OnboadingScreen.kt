package com.noxinfinity.pdate.ui.screens.onboading

import ChatHeart
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.ui.common.components.GradientButton

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color(0xff000000),
            darkIcons = useDarkIcons,
        )
        onDispose {}
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = modifier.fillMaxSize(),
            contentDescription = "Onboarding",
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.onboarding_thumbnail)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.hsl(0f, 0f, 0f, 0.5f)
                        ),
                        startY = 0f,
                        endY = 500f
                    )
                )
                .align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(ChatHeart, contentDescription = null, tint = Color(0xffffa2a5), modifier = Modifier.size(80.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Chào mừng đến với P-Date",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Tìm kiếm mảnh ghép phù hợp cho trái tim đơn côi giữa mùa đông lạnh lẽo...",
                color = Color(0xffa6a3a4),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            GradientButton("Bắt đầu", modifier = Modifier.padding(top = 16.dp), colors = listOf(Color(0xfffeadd4), Color(0xfffea6a4)), labelFontSize = 18.sp, textModifier = Modifier.width(200.dp)) {
                Log.d("OnboardingScreen", "Button Clicked")
            }
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen()
}