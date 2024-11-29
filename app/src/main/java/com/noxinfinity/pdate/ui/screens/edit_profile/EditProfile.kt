package com.noxinfinity.pdate.ui.screens.edit_profile

import android.widget.GridView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.ui.common.components.AppIndicator
import com.noxinfinity.pdate.ui.common.components.NetworkImage
import com.noxinfinity.pdate.ui.screens.edit_profile.components.EditProfileContainer
import com.noxinfinity.pdate.ui.screens.edit_profile.components.ProfilePictureContainer
import com.noxinfinity.pdate.ui.screens.edit_profile.components.PurposeModalBottomSheet
import com.noxinfinity.pdate.ui.view_models.edit_profile.EditProfileViewModel
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.helper.ImageHelper
import compose.icons.FeatherIcons
import compose.icons.feathericons.Target
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(modifier: Modifier = Modifier) {
    val viewModel: EditProfileViewModel = hiltViewModel()
    val state = viewModel.uiState.collectAsState()
    val user = state.value.user

    val context = LocalContext.current

    val uploadAvatar = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let {
            viewModel.uploadAvatar(
                ImageHelper.uriToFile(context, it)
            )
        }
    }

    val uploadPicture = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let {
            viewModel.uploadPicture(
                ImageHelper.uriToFile(context, it)
            )
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val purposeSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    Scaffold (
        modifier = modifier
            .fillMaxSize(),
        containerColor = Color(0xfff3f3f3)
    ) { paddingValues ->
        if(state.value.isFetching){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AppIndicator()
            }
        } else {
            Column(
                modifier = Modifier.padding(paddingValues).verticalScroll(
                    state = rememberScrollState()
                )
            ) {
                40.heightPadding()
                Box(Modifier.fillMaxWidth(), Alignment.Center) {
                    NetworkImage(
                        url = user.avatar ?: "",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                uploadAvatar.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },
                    )
                }

                15.heightPadding()

                Text(
                    "Ảnh",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 12.dp)
                )
                10.heightPadding()

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    userScrollEnabled = false,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        horizontal = 12.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(
                        max = ((12 * 120)).dp
                    )
                ) {
                    items(9) { index ->
                        ProfilePictureContainer(
                            picture = if(index < (user.pictures?.size ?: 0)) user.pictures?.get(index) else null,
                            addPicture = {
                                uploadPicture.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                        )
                    }
                }

                15.heightPadding()

                EditProfileContainer(
                    title = "Mục đích",
                    content = user.purpose?.joinToString(
                        separator = ", "
                    ) {
                        it?.name ?: ""
                    },
                    icon = {
                        Box(
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                FeatherIcons.Target,
                                contentDescription = null,
                            )
                        }
                    },
                    onClick = {
                        coroutineScope.launch {
                            purposeSheetState.show()
                            viewModel.getAllPurpose()
                        }
                    }
                )

            }
        }

        if (purposeSheetState.isVisible) {
            PurposeModalBottomSheet(
                sheetState = purposeSheetState,
                isLoading = state.value.isLoading,
                allItems = state.value.purposeList,
                chosenItem = user.purpose ?: listOf(),
                onSave = {
                    viewModel.updatePurpose(it)
                }
            )
        }
    }


}

