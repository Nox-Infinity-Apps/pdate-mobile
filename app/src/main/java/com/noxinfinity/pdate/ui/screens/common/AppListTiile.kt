package com.noxinfinity.pdate.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noxinfinity.pdate.ui.common.components.NetworkImage
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.widthPadding

@Composable
fun AppListTile(
    modifier: Modifier = Modifier,
    url: String? = null,
    assets: Int? = null,
    icon:  ImageVector?,
    iconModifier: Modifier? = Modifier,
    headerTitle: String?,
    title: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(headerTitle ?: "", color=Color(0xFF4B4E52), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Box{
                if(icon != null){
                    Icon(
                        icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF798595)
                    )
                }else {
                    if (url != null) {
                        NetworkImage(
                            url = url,
                            modifier = Modifier.size(30.dp)
                        )
                    } else if (assets != null) {
                        Image(
                            painter = painterResource(assets),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            8.widthPadding()

            Text(
                text = title,
                color = Color(0xFF404852),
                maxLines = 3,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }

        8.heightPadding()

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0x0C000000),
        )

        5.heightPadding()
    }
}