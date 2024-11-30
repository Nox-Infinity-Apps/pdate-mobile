package com.noxinfinity.pdate.ui.view_models.edit_profile

import com.noxinfinity.pdate.GetListGradeQuery
import com.noxinfinity.pdate.GetListMajorQuery
import com.noxinfinity.pdate.GetUserInfoQuery
import com.noxinfinity.pdate.ui.view_models.profile.initialData

data class EditProfileState (
    val user: GetUserInfoQuery.Data1 = initialData,
    val purposeList: List<GetUserInfoQuery.Purpose?> = listOf(),
    val hobbiesList: List<GetUserInfoQuery.Hobby?> = listOf(),
    val majorList: List<GetUserInfoQuery.Major?> = listOf(),
    val gradeList: List<GetUserInfoQuery.Grade?> = listOf(),
    val isLoading: Boolean = false,
    val isFetching: Boolean = true,
    val isFilling: Boolean = false
)

