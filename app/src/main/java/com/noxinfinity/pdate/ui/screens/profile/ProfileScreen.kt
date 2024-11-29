package com.noxinfinity.pdate.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Locate
import com.composables.icons.lucide.Lucide
import com.noxinfinity.pdate.LoginByGoogleMutation
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.ui.common.components.AppIndicator
import com.noxinfinity.pdate.ui.common.components.NetworkImage
import com.noxinfinity.pdate.ui.screens.common.AppListTile
import com.noxinfinity.pdate.ui.view_models.profile.ProfileViewModel
import com.noxinfinity.pdate.utils.getString
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.helper.DateTimeHelper
import com.noxinfinity.pdate.utils.widthPadding
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Edit
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, onSignOut: () -> Unit, user: LoginByGoogleMutation.User, toEditProfileScreen: () -> Unit) {

    val viewModel: ProfileViewModel = hiltViewModel()
    val viewState = viewModel.uiState.collectAsState()


    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )


    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                IconButton(
                    onClick = toEditProfileScreen,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        FontAwesomeIcons.Solid.Edit,
                        modifier = Modifier.padding(6.dp),
                        contentDescription = null
                    )
                }
            }

        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NetworkImage(
                    url = user.avatar ?: "",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                )
                16.heightPadding()
                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                6.heightPadding()

                Button(
                    onClick = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                        viewModel.fetchUser()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        )
                ) {
                    Text("Xem profile cá nhân")
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        onSignOut()
                    },
                    colors = ButtonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        )
                ) {
                    Text("Đăng xuất")
                }

                12.heightPadding()

            }
        }

        if (sheetState.isVisible) {

            ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch { sheetState.hide() }
                },
                sheetState = sheetState,
                containerColor = Color.White,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if(viewState.value.isLoading ){
                        AppIndicator()
                    } else {
                        val item = viewState.value.user
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            NetworkImage(
                                url = item.avatar ?: "",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape),
                            )
                            16.heightPadding()
                            Text(
                                text = item.fullName,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            6.heightPadding()
                            Text(
                                text = item.bio ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )

                            16.heightPadding()

                            if(!item.purpose.isNullOrEmpty()) {
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

                                    15.widthPadding()

                                    Text(
                                        text = "Mục đích",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }

                            12.heightPadding()

                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                item.purpose?.forEach {
                                    AssistChip(
                                        onClick = { /* Handle click */ },
                                        label = { Text(it?.name ?: "") },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = Color(0xFFF1F1F1),
                                            labelColor = Color.Black,
                                        ),
                                        border = null
                                    )
                                }
                            }

                            16.heightPadding()

                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier.size(25.dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.user),
                                        contentDescription = null
                                    )
                                }

                                15.widthPadding()

                                Text(
                                    text = "Thông tin chính",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                )
                            }

                            12.heightPadding()

                            Column(
                                modifier = Modifier.padding(
                                    horizontal = 10.dp
                                )
                            ) {
                                AppListTile(
                                    assets = R.drawable.gender,
                                    title = item.gender?.getString() ?: "",
                                    headerTitle = "Khoảng cách",
                                    icon = Lucide.Locate
                                )
                                AppListTile(
                                    assets = R.drawable.location,
                                    title = "${item.dob?.let { DateTimeHelper.formatToDDMMYYYY(it) }}",
                                    headerTitle = "Khoảng cách",
                                    icon = Lucide.Locate
                                )
                                AppListTile(
                                    assets = R.drawable.student,
                                    title = item.grade?.name ?: "",
                                    headerTitle = "Khoảng cách",
                                    icon = Lucide.Locate
                                )
                                AppListTile(
                                    assets = R.drawable.major,
                                    title = item.major?.name ?: "",
                                    headerTitle = "Khoảng cách",
                                    icon = Lucide.Locate
                                )
                            }

                            20.heightPadding()

                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                item.hobbies?.forEach {
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

                            10.heightPadding()

                            Text(
                                text = "Photos",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth()
                            )

                            12.heightPadding()

                            item.pictures?.let {
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
                                            url = it?.url ?: "",
                                            modifier = Modifier
                                                .height(150.dp)
                                                .clip(RoundedCornerShape(18.dp)),
                                        )
                                    }
                                }
                            }
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
}
