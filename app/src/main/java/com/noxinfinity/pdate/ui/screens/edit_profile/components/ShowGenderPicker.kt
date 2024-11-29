package com.noxinfinity.pdate.ui.screens.edit_profile.components

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noxinfinity.pdate.type.Gender
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.widthPadding
import compose.icons.FeatherIcons
import compose.icons.feathericons.Image
import compose.icons.feathericons.X
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowGenderPicker(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onSelect: (Gender) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = Color.White,
        onDismissRequest = {
            coroutineScope.launch { sheetState.hide() }
        },
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp)
        ) {
            IconButton(
                modifier = Modifier.size(35.dp),
                colors = IconButtonColors(
                    containerColor = Color(0xFFf4f4f4),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray,
                ),
                onClick = {
                    coroutineScope.launch { sheetState.hide() }
                }
            ) {
                Icon(
                    FeatherIcons.X,
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

        }

        12.heightPadding()

        Text(
            text = "Giới tính",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 12.dp)
        )

        8.heightPadding()

        Text(
            text = "Bạn có thể tiết lộ hoặc không?",
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 12.dp)
        )

        12.heightPadding()

        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        onSelect(Gender.MALE)
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    20.widthPadding()
                    Text(
                        "Nam",
                        color = Color(0xff25282b),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        onSelect(Gender.FEMALE)
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    20.widthPadding()
                    Text(
                        "Nữ",
                        color = Color(0xff25282b),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        onSelect(Gender.OTHER)
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    20.widthPadding()
                    Text(
                        "Khác",
                        color = Color(0xff25282b),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

    }
}