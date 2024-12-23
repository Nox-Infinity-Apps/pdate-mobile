package com.noxinfinity.pdate.ui.screens.edit_profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noxinfinity.pdate.GetUserInfoQuery
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.ui.common.components.AppIndicator
import com.noxinfinity.pdate.utils.heightPadding
import compose.icons.FeatherIcons
import compose.icons.feathericons.X
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PurposeModalBottomSheet(
    sheetState: SheetState,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    chosenItem: List<GetUserInfoQuery.Purpose?> = listOf(),
    allItems: List<GetUserInfoQuery.Purpose?> = listOf(),
    onSave: (List<Int>) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    var currentItems by remember {
        mutableStateOf(chosenItem)
    }

    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = Color.White,
        onDismissRequest = {
            coroutineScope.launch { sheetState.hide() }
        },
        modifier = modifier.fillMaxWidth()
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.height(50.dp).fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                AppIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp
                    )
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
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

                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                            onSave(
                                currentItems.map {
                                    it?.id ?: 0
                                }
                            )
                        }
                    ) {
                        Text(
                            text = "Lưu",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }

                12.heightPadding()

                Text(
                    text = "Mục đích",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                8.heightPadding()

                Text(
                    text = "Bạn muốn tìm một mối quan hệ như thế nào?",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )

                12.heightPadding()

                FlowRow {
                    allItems.map {
                        AssistChip(
                            label = {
                                Text(
                                    text = it?.name ?: "",
                                    fontSize = 18.sp,
                                    fontWeight = if(it in currentItems) FontWeight.Bold else FontWeight.Light,
                                    color = if(it in currentItems) Color(0xff36353a) else Color(0xff88898b),

                                )
                            },
                            border = BorderStroke(
                                width = 1.dp,
                                color = if(it in currentItems) Color(0xffcc7982) else Color.Gray
                            ),
                            shape = RoundedCornerShape(50.dp),
                            onClick = {
                                currentItems = if (it in currentItems) {
                                    currentItems.filter { item ->
                                        item?.id != it?.id
                                    }
                                } else {
                                    currentItems + it
                                }
                            },

                            modifier = Modifier.padding(end = 10.dp)
                        )
                    }
                }

                20.heightPadding()

            }
        }

    }
}

