package com.noxinfinity.pdate.ui.screens.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Blinds
import com.composables.icons.lucide.Book
import com.composables.icons.lucide.Cake
import com.composables.icons.lucide.Compass
import com.composables.icons.lucide.Folder
import com.composables.icons.lucide.Info
import com.composables.icons.lucide.Locate
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.VenetianMask
import com.noxinfinity.customtinderswiper.swipe_state.SwipeDirection
import com.noxinfinity.customtinderswiper.swipe_state.SwipeableCardState
import com.noxinfinity.customtinderswiper.swipe_state.swiper
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.SuggestedUsersQuery
import com.noxinfinity.pdate.ui.common.components.NetworkImage
import com.noxinfinity.pdate.ui.screens.common.AppListTile
import com.noxinfinity.pdate.ui.view_models.home.HomeEvent
import com.noxinfinity.pdate.ui.view_models.home.HomeState
import com.noxinfinity.pdate.utils.getString
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.helper.DateTimeHelper
import com.noxinfinity.pdate.utils.widthPadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeCardList(
    modifier: Modifier = Modifier,
    state: HomeState,
    swipeableCardState: SwipeableCardState,
    onTriggerEvent: (HomeEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val coroutineScope = rememberCoroutineScope()

    val items = state.profileList

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val scope = rememberCoroutineScope()

        if (state.isLoading || state.isFetching) {
            HomeCardSkeletonWithShimmer()
        }
        if (items.isEmpty()) {
            HomeCardSkeletonWithShimmer()
        } else if (items.size == 1) {
            HomeCardItem(
                profileData = items.first()!!,
                openSheet = {
                    coroutineScope.launch {
                        sheetState.show()
                    }
                },
                modifier = Modifier.swiper(
                    state = swipeableCardState,
                    onSwiped = {
                        when (it) {
                            SwipeDirection.Left -> {
                                onTriggerEvent(HomeEvent.UnLike())
                                onTriggerEvent(HomeEvent.PopUp)
                            }

                            SwipeDirection.Right -> {
                                onTriggerEvent(HomeEvent.Like())
                                onTriggerEvent(HomeEvent.PopUp)
                            }

                            SwipeDirection.Up -> {
                                onTriggerEvent(HomeEvent.PopUp)
                            }

                            SwipeDirection.Down -> {}
                        }
                        scope.launch {
                            swipeableCardState.snapTo(Offset.Zero)
                        }
                    }
                )
            )
        } else {
            items.take(2).reversed().forEachIndexed { index, item ->

                if (index == 1) {
                    HomeCardItem(
                        profileData = item!!,
                        openSheet = {
                            coroutineScope.launch {
                                sheetState.show()
                            }
                        },
                        modifier = Modifier.swiper(
                            state = swipeableCardState,
                            onSwiped = {

                                when (it) {
                                    SwipeDirection.Left -> {
                                        onTriggerEvent(HomeEvent.UnLike())
                                        onTriggerEvent(HomeEvent.PopUp)
                                    }

                                    SwipeDirection.Right -> {
                                        onTriggerEvent(HomeEvent.Like())
                                        onTriggerEvent(HomeEvent.PopUp)
                                    }

                                    SwipeDirection.Up -> {
                                        onTriggerEvent(HomeEvent.PopUp)
                                    }

                                    SwipeDirection.Down -> {}
                                }

                                scope.launch {
                                    swipeableCardState.snapTo(Offset.Zero)
                                }
                            }
                        )
                    )
                } else {
                    HomeCardItem(
                        profileData = item!!,
                        openSheet = {
                            coroutineScope.launch {
                                sheetState.show()
                            }
                        },
                        modifier = Modifier.graphicsLayer {
                            scaleX = swipeableCardState.scale
                            scaleY = swipeableCardState.scale
                        }
                    )
                }


            }
        }

        if (sheetState.isVisible) {
            val item = items.first()

            ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch { sheetState.hide() }
                },
                sheetState = sheetState,
                containerColor = Color(0xFFF1F1F1),
                modifier = Modifier.padding()
                ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(modifier = Modifier.background(Color.White).fillMaxWidth().padding(
                            top = 40.dp
                        ),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            NetworkImage(
                                url = item!!.avatarUrl ?: "",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape),
                            )
                            16.heightPadding()
                            Text(
                                text = item.fullName ?: "",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )
                            10.heightPadding()
                            Text(
                                text = item.bio ?: "",
                                color = Color.Gray,
                                fontSize = 15.sp
                            )

                            30.heightPadding()

                            if (!item.purpose.isNullOrEmpty()) {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier.size(25.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.purpose),
                                            contentDescription = null
                                        )
                                    }

                                    10.widthPadding()

                                    Text(
                                        text = "Mục đích",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }

                        2.heightPadding()

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            item?.purpose?.forEach {
                                AssistChip(
                                    onClick = { /* Handle click */ },
                                    label = { Text(it ?: "") },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = Color(0xFFF1F1F1),
                                        labelColor = Color.Black,
                                    ),
                                    border = null
                                )
                            }
                        }

                        5.heightPadding()

                        Column(
                            modifier = Modifier.fillMaxWidth().background(Color.White).
                            padding(
                                vertical = 16.dp,
                                horizontal = 10.dp
                            )
                        ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {


                            5.widthPadding()

                            Text(
                                text = "Thông tin chính",
                                color = Color(0xFF303236),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                        12.heightPadding()

                        Column(
                            modifier = Modifier.padding(
                                horizontal = 10.dp
                            )
                        ) {
                            AppListTile(
                                title = "%.1f km".format(item?.distance?.div(1000.0)),
                                headerTitle = "Khoảng cách",
                                icon = Lucide.Compass
                            )
                            AppListTile(
                                assets = R.drawable.gender,
                                title = item?.gender?.getString() ?: "",
                                headerTitle = "Giới tính",
                                icon = Lucide.VenetianMask
                            )
                            AppListTile(
                                assets = R.drawable.location,
                                title = "${item?.dob?.let { DateTimeHelper.formatToDDMMYYYY(it) }}",
                                headerTitle = "Sinh nhật",
                                icon = Lucide.Cake
                            )
                            AppListTile(
                                assets = R.drawable.student,
                                title = item?.grade?.name ?: "",
                                headerTitle = "Khoá",
                                icon = Lucide.Book
                            )
                            AppListTile(
                                assets = R.drawable.major,
                                title = item?.major?.name ?: "",
                                headerTitle = "Ngành",
                                icon = Lucide.Blinds
                            )
                        }
                        }

                        8.heightPadding()

                        Column(modifier = Modifier.fillMaxWidth().padding(
                        ).background(Color.White)) {
                            Row(modifier = Modifier.padding(
                                top = 12.dp,
                                start = 12.dp
                            )) {
                                Text(
                                    text = "Sở thích",
                                    color = Color(0xFF303236),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                            FlowRow(
                                modifier = Modifier.fillMaxWidth().padding(
                                    bottom = 10.dp,
                                    start = 12.dp,
                                    end = 12.dp
                                ),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                item?.allHobbies?.forEach {
                                    AssistChip(
                                        onClick = { /* Handle click */ },
                                        label = { Text(it?.title ?: "") },
                                        leadingIcon = {
                                            NetworkImage(
                                                url = it?.iconUrl ?: "",
                                                modifier = Modifier.size(16.dp)
                                            )
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = Color(0xFFF1F1F1),
                                            labelColor = Color.Black,
                                        ),
                                        border = null
                                    )
                                }
                            }
                        }

                        10.heightPadding()

                        Column(modifier = Modifier.background(Color.White).padding(
                            12.dp
                        )) {
                            Text(
                                text = "Ảnh",
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth(),
                                color = Color(0xFF303236),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )

                            12.heightPadding()

                            item?.pictures?.let {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(3),
                                    userScrollEnabled = false,
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.heightIn(
                                        max = ((item.pictures.size * 120)).dp
                                    )
                                ) {
                                    items(item.pictures) {
                                        NetworkImage(
                                            url = it ?: "",
                                            modifier = Modifier
                                                .height(150.dp)
                                                .clip(RoundedCornerShape(18.dp)),
                                        )
                                    }
                                }
                            }
                        }

                        12.heightPadding()

                        Row(modifier = Modifier.background(Color.White).fillMaxWidth().padding(
                            vertical = 18.dp
                        ).clickable {
                            coroutineScope.launch {
                                launch {
                                    onTriggerEvent(HomeEvent.Block(item?.fcmId ?: ""))
                                }.join()
                                sheetState.hide()
                                onTriggerEvent(HomeEvent.PopUp)
                            }
                        },
                            horizontalArrangement = Arrangement.Center
                            ) {
                            Text("Chặn người này", color = Color.Red, fontWeight = FontWeight.Bold)
                        }
                        12.heightPadding()
                    }

                    Box(
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .size(25.dp)
                            .align(
                                Alignment.TopEnd
                            )
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { sheetState.hide() }
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.close),
                                contentDescription = null
                            )
                        }
                    }


                }

            }
        }
    }
}