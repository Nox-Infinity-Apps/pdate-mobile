package com.noxinfinity.pdate.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.ui.common.components.NetworkImage
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.widthPadding

@Composable
fun AppListTile(
    modifier: Modifier = Modifier,
    url: String? = null,
    assets: Int? = null,
    title: String,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Box(
                modifier = Modifier.size(25.dp)
            ) {
                if(url != null){
                    NetworkImage(
                        url = url,
                        modifier = Modifier.size(30.dp)
                    )
                } else if(assets != null){
                    Image(
                        painter = painterResource(assets),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            15.widthPadding()

            Text(
                text = title,
                color = Color.Black,
                maxLines = 3
            )
        }

        8.heightPadding()

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
        )

        5.heightPadding()
    }
}