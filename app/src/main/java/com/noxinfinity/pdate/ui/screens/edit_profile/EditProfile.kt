package com.noxinfinity.pdate.ui.screens.edit_profile

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.composables.icons.lucide.BookOpen
import com.composables.icons.lucide.Cake
import com.composables.icons.lucide.GraduationCap
import com.composables.icons.lucide.Lucide
import com.noxinfinity.pdate.navigation.Graph
import com.noxinfinity.pdate.type.Gender
import com.noxinfinity.pdate.ui.common.components.AppIndicator
import com.noxinfinity.pdate.ui.common.components.NetworkImage
import com.noxinfinity.pdate.ui.screens.edit_profile.components.DatePickerModal
import com.noxinfinity.pdate.ui.screens.edit_profile.components.EditProfileContainer
import com.noxinfinity.pdate.ui.screens.edit_profile.components.HobbiesModalBottomSheet
import com.noxinfinity.pdate.ui.screens.edit_profile.components.ProfilePictureContainer
import com.noxinfinity.pdate.ui.screens.edit_profile.components.PurposeModalBottomSheet
import com.noxinfinity.pdate.ui.screens.edit_profile.components.ShowGenderPicker
import com.noxinfinity.pdate.ui.screens.edit_profile.components.ShowGradeModalBottomSheet
import com.noxinfinity.pdate.ui.screens.edit_profile.components.ShowMajorModalBottomSheet
import com.noxinfinity.pdate.ui.view_models.edit_profile.EditProfileViewModel
import com.noxinfinity.pdate.utils.getString
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.helper.DateTimeHelper
import com.noxinfinity.pdate.utils.helper.ImageHelper
import com.noxinfinity.pdate.utils.widthPadding
import compose.icons.FeatherIcons
import compose.icons.feathericons.Book
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.ChevronLeft
import compose.icons.feathericons.Edit2
import compose.icons.feathericons.Hash
import compose.icons.feathericons.Image
import compose.icons.feathericons.Mail
import compose.icons.feathericons.PlusCircle
import compose.icons.feathericons.Target
import compose.icons.feathericons.User
import compose.icons.feathericons.Users
import compose.icons.feathericons.X
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSave: () -> Unit,
    navController: NavController,
    canReturn: Boolean = true
) {
    val viewModel: EditProfileViewModel = hiltViewModel()
    val state = viewModel.uiState.collectAsState().value
    val user = state.user
    var currentImage = ""

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

    val updatePictureById = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let {
            viewModel.updatePictureById(
                ImageHelper.uriToFile(context, it),
                currentImage
            )
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val purposeSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val editPictureSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val hobbiesSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val genderSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val gradeSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val majorSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    var fullNameText by remember {
        mutableStateOf(user.fullName)
    }

    var bioText by remember {
        mutableStateOf(user.bio ?: "")
    }

    var dobText by remember {
        mutableStateOf(user.dob ?: "")
    }

    var email by remember {
        mutableStateOf(user.email)
    }

    var grade by remember {
        mutableStateOf(user.grade)
    }

    var major by remember {
        mutableStateOf(user.major)
    }

    var showDateModal by remember { mutableStateOf(false) }


    var gender by remember {
        mutableStateOf(user.gender ?: Gender.OTHER)
    }

    LaunchedEffect(Unit) {
        Log.d("FETCH_USER HOME", state.toString())
        snapshotFlow { state }
            .collect { value ->
                if (value.isFilling) {
                    Log.d("FETCH_USER HOME", value.toString())
                    fullNameText = value.user.fullName
                    bioText = value.user.bio ?: ""
                    dobText = value.user.dob ?: ""
                    email = value.user.email
                    grade = value.user.grade
                    major = value.user.major
                }

            }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xfff3f3f3),
        topBar = {
            TopAppBar(
                title = {
                    Text("Chỉnh sửa profile")
                },
                navigationIcon = {
                    if (canReturn) {
                        IconButton(
                            onClick = onBack
                        ) {
                            Icon(
                                FeatherIcons.ChevronLeft,
                                contentDescription = null,
                            )
                        }
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (fullNameText.isNotEmpty() &&
                                bioText.isNotEmpty() &&
                                dobText.isNotEmpty() &&
                                email.isNotEmpty() &&
                                grade != null &&
                                major != null &&
                                user.gender != null && !user.purpose.isNullOrEmpty() && !user.hobbies.isNullOrEmpty()
                            ) {
                                coroutineScope.launch {
                                    launch {
                                        viewModel.updateUser(
                                            gender = gender,
                                            grade = grade!!.id!!,
                                            bio = bioText,
                                            email = email,
                                            fullName = fullNameText,
                                            dob = dobText,
                                            major = major!!.id!!
                                        )
                                    }.join()
                                    if (canReturn) {
                                        navController.popBackStack()
                                    } else {
                                        navController.navigate(Graph.MAIN)
                                    }
                                    onSave()
                                }
                            }
                        }
                    ) {
                        Text("Lưu", fontSize = 20.sp)
                    }
                }
            )
        }
    ) { paddingValues ->
        if (state.isFetching) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AppIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(
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
                    color = Color(0xff25282b),
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
                            picture = if (index < (user.pictures?.size ?: 0)) user.pictures?.get(
                                index
                            ) else null,
                            addPicture = {
                                uploadPicture.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },
                            editPicture = {
                                coroutineScope.launch {
                                    currentImage = it
                                    editPictureSheetState.show()
                                }
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
                        Icon(
                            FeatherIcons.Target,
                            contentDescription = null,
                            tint = Color(0xff797f87),
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            purposeSheetState.show()
                            viewModel.getAllPurpose()
                        }
                    }
                )

                15.heightPadding()

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileTitle(
                        "Sở thích",
                    )
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            viewModel.getAllHobbies()
                            coroutineScope.launch {
                                hobbiesSheetState.show()
                            }
                        },
                    ) {
                        Icon(
                            FeatherIcons.PlusCircle,
                            contentDescription = null,
                            tint = Color(0xff797f87),
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }

                8.heightPadding()

                Surface(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xffdbdadf),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp, top = 2.dp, bottom = 2.dp)

                ) {
                    if (user.hobbies.isNullOrEmpty()) {
                        Text(
                            "Bạn chưa có sở thích nào.",
                            color = Color(0xff25282b),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(
                                vertical = 16.dp,
                                horizontal = 12.dp
                            )
                        )
                    } else {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, top = 10.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {

                            user.hobbies.forEach {
                                AssistChip(
                                    onClick = { /* Handle click */ },
                                    label = {
                                        Text(
                                            it?.title ?: "",
                                            color = Color(0xff25282b),
                                            fontWeight = FontWeight.Bold,
                                        )
                                    },
                                    leadingIcon = {
                                        NetworkImage(
                                            url = it?.iconUrl ?: "",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = Color(0xFFf4f4f4),
                                        labelColor = Color.Black,
                                    ),
                                    border = null,
                                )
                            }

                        }
                    }

                }

                15.heightPadding()

                ProfileTitle("Tên")

                10.heightPadding()

                ProfileContent {
                    ProfileTextField(
                        value = fullNameText,
                        onChange = {
                            fullNameText = it
                        },
                        leadingIcon = {
                            Icon(
                                FeatherIcons.User,
                                tint = Color(0xff797f87),
                                contentDescription = null
                            )
                        }
                    )
                }

                15.heightPadding()

                ProfileTitle("Giới thiệu bản thân")

                10.heightPadding()

                ProfileContent {
                    ProfileTextField(
                        value = bioText,
                        onChange = {
                            bioText = it
                        },
                        singleLine = false,
                        leadingIcon = {
                            Icon(
                                FeatherIcons.Edit2,
                                tint = Color(0xff797f87),
                                contentDescription = null
                            )
                        }
                    )
                }

                15.heightPadding()

                EditProfileContainer(
                    title = "Ngày sinh",
                    onClick = {
                        showDateModal = true
                    },
                    icon = {
                        Icon(
                            Lucide.Cake,
                            tint = Color(0xff797f87),
                            contentDescription = null
                        )
                    },
                    content = DateTimeHelper.formatToDDMMYYYY(dobText)
                )

                15.heightPadding()

                EditProfileContainer(
                    title = "Giới tính",
                    onClick = {
                        coroutineScope.launch {
                            genderSheetState.show()
                        }
                    },
                    icon = {
                        Icon(
                            FeatherIcons.Users,
                            tint = Color(0xff797f87),
                            contentDescription = null
                        )
                    },
                    content = gender.getString()
                )

                15.heightPadding()

                ProfileTitle("Email")

                10.heightPadding()

                ProfileContent {
                    ProfileTextField(
                        value = email,
                        onChange = {
                            email = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                        ),
                        leadingIcon = {
                            Icon(
                                FeatherIcons.Mail,
                                tint = Color(0xff797f87),
                                contentDescription = null
                            )
                        }
                    )
                }

                15.heightPadding()

                EditProfileContainer(
                    title = "Ngành",
                    content = major?.name ?: "",
                    icon = {
                        if (major?.iconUrl == null) {
                            Icon(
                                Lucide.BookOpen,
                                contentDescription = null,
                                tint = Color(0xff797f87),
                            )
                        } else {
                            NetworkImage(
                                url = major?.iconUrl ?: "",
                                modifier = Modifier.size(30.dp)
                            )
                        }

                    },
                    onClick = {
                        coroutineScope.launch {
                            majorSheetState.show()
                            viewModel.getListMajor()
                        }
                    }
                )

                15.heightPadding()

                EditProfileContainer(
                    title = "Khóa",
                    content = grade?.name ?: "",
                    icon = {
                        Icon(
                            Lucide.GraduationCap,
                            contentDescription = null,
                            tint = Color(0xff797f87),
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            gradeSheetState.show()
                            viewModel.getListGrade()
                        }
                    }
                )

                20.heightPadding()

            }
        }

        if (purposeSheetState.isVisible) {
            PurposeModalBottomSheet(
                sheetState = purposeSheetState,
                isLoading = state.isLoading,
                allItems = state.purposeList,
                chosenItem = user.purpose ?: listOf(),
                onSave = {
                    viewModel.updatePurpose(it)
                }
            )
        }

        if (editPictureSheetState.isVisible) {
            ModalBottomSheet(
                sheetState = editPictureSheetState,
                containerColor = Color.White,
                onDismissRequest = {
                    coroutineScope.launch { editPictureSheetState.hide() }
                },
                modifier = modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {
                                coroutineScope.launch { editPictureSheetState.hide() }
                                updatePictureById.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            20.widthPadding()
                            Icon(
                                FeatherIcons.Image,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                            15.widthPadding()
                            Text(
                                "Thay Ảnh",
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
                                coroutineScope.launch {
                                    editPictureSheetState.hide()
                                }
                                viewModel.deletePictureById(
                                    currentImage
                                )
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            20.widthPadding()
                            Icon(
                                FeatherIcons.X,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                            15.widthPadding()
                            Text(
                                "Xóa Ảnh",
                                color = Color(0xff25282b),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }

        if (hobbiesSheetState.isVisible) {
            HobbiesModalBottomSheet(
                sheetState = hobbiesSheetState,
                isLoading = state.isLoading,
                allItems = state.hobbiesList,
                chosenItem = user.hobbies ?: listOf(),
                onSave = {
                    viewModel.updateHobbies(it)
                }
            )
        }

        if (showDateModal) {
            DatePickerModal(
                onDismiss = {
                    showDateModal = false
                },
                onDateSelected = {
                    if (it != null) {
                        dobText = DateTimeHelper.convertMilisToDate(it)
                    }
                }
            )
        }

        if (genderSheetState.isVisible) {
            ShowGenderPicker(
                sheetState = genderSheetState,
                onSelect = {
                    gender = it
                    coroutineScope.launch {
                        genderSheetState.hide()
                    }
                }
            )
        }

        if (gradeSheetState.isVisible) {
            ShowGradeModalBottomSheet(
                sheetState = gradeSheetState,
                onSelect = {
                    coroutineScope.launch {
                        grade = it
                        gradeSheetState.hide()
                    }
                },
                isLoading = state.isLoading,
                listGrade = state.gradeList
            )
        }

        if (majorSheetState.isVisible) {
            ShowMajorModalBottomSheet(
                sheetState = majorSheetState,
                onSelect = {
                    major = it
                    coroutineScope.launch {
                        majorSheetState.hide()
                    }
                },
                isLoading = state.isLoading,
                listMajor = state.majorList
            )
        }

    }
}

@Composable
fun ProfileTitle(title: String) {
    Text(
        title,
        fontWeight = FontWeight.Bold,
        color = Color(0xff25282b),
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 12.dp)
    )
}

@Composable
fun ProfileContent(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xffdbdadf),
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 2.dp, bottom = 2.dp)
    ) {
        content()
    }
}

@Composable
fun ProfileTextField(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    leadingIcon: @Composable () -> Unit = {},
) {
    TextField(
        value = value,
        onValueChange = {
            onChange(it)
        },
        isError = value.isEmpty(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color(0xff25282b),
            unfocusedIndicatorColor = Color.Gray,
            errorIndicatorColor = Color.Red,
            errorContainerColor = Color(0xffFFCCCB),
            focusedPlaceholderColor = Color.Transparent,
//            errorContainerColor = Color.Red,
        ),
        readOnly = readOnly,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}