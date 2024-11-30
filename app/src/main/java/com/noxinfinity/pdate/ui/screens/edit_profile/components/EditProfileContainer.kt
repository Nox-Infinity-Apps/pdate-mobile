package com.noxinfinity.pdate.ui.screens.edit_profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.widthPadding
import compose.icons.AllIcons
import compose.icons.FeatherIcons
import compose.icons.FontAwesomeIcons
import compose.icons.feathericons.ArrowRight
import compose.icons.feathericons.ChevronRight
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.ArrowAltCircleRight

@Composable
fun EditProfileContainer(
    title: String,
    icon: @Composable () -> Unit,
    content: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            title,
            fontWeight = FontWeight.Bold,
            color = Color(0xff25282b),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 12.dp)
        )
        10.heightPadding()
        Surface(
            border = BorderStroke(
                width = 1.dp,
                color = Color(0xffdbdadf),
            ),
            modifier = Modifier.fillMaxWidth().padding(start = 0.dp, end = 0.dp, top = 2.dp, bottom = 2.dp).clickable {
                onClick()
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                       color =  if(content.isNullOrEmpty()) Color(0xffFFCCCB) else Color.White
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon().apply {
                    modifier.size(30.dp)
                }
                15.widthPadding()
                Text(
                    content ?: "",
                    modifier = Modifier.weight(1f),
                    color = Color(0xff797f87),
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp
                )
                10.widthPadding()
                Icon(
                    FeatherIcons.ChevronRight,
                    contentDescription = "Edit",
                    modifier = Modifier.size(15.dp)
                )

            }
        }

    }
}
