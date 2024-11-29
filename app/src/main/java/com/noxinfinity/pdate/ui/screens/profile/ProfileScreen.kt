package com.noxinfinity.pdate.ui.screens.profile

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.navigation.Graph
import com.noxinfinity.pdate.ui.common.components.AppIndicator
import com.noxinfinity.pdate.ui.common.components.NetworkImage
import com.noxinfinity.pdate.ui.screens.common.AppListTile
import com.noxinfinity.pdate.ui.screens.theme.DatingApplicationTheme
import com.noxinfinity.pdate.ui.view_models.profile.ProfileViewModel
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.helper.DateTimeHelper
import com.noxinfinity.pdate.utils.widthPadding
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Edit


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, onSignOut: () -> Unit) {

    val profileViewModel: ProfileViewModel = hiltViewModel()

    val viewState = profileViewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ){
        if(viewState.value.isLoading) {
            AppIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            val item = viewState.value.user
            Scaffold(
                topBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        IconButton(
                            onClick = {
                            },
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
                            .verticalScroll(rememberScrollState()
                            ),
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
                            text = item.fullName ?: "",
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
//                        AppListTile(
//                            assets = R.drawable.gender,
//                            title = "${item.gender}",
//                        )
                            AppListTile(
                                assets = R.drawable.location,
                                title = "${item.dob?.let { DateTimeHelper.formatToDDMMYYYY(it) }}",
                            )
                            AppListTile(
                                assets = R.drawable.student,
                                title = item.grade?.name ?: "",
                            )
                            AppListTile(
                                assets = R.drawable.major,
                                title = item.major?.name ?: "",
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

                        12.heightPadding()

                        Button(
                            onClick = {
                                onSignOut()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 6.dp
                                )
                        ) {
                            Text("Đăng xuất")
                        }

                    }
                }
            }
        }
    }
}
