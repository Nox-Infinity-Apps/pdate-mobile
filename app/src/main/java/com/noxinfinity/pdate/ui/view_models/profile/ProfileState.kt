package com.noxinfinity.pdate.ui.view_models.profile

import com.noxinfinity.pdate.GetUserInfoQuery
import com.noxinfinity.pdate.type.Gender

data class ProfileState (
    val user: GetUserInfoQuery.Data1 = initialData,
    val isLoading: Boolean = false,
)

val initialData  = GetUserInfoQuery.Data1(
    avatar = "",
    bio = "",
    dob = "",
    email = "",
    fullName = "",
    grade = null,
    hobbies = null,
    major = null,
    pictures = null,
    purpose = null,
    gender = Gender.MALE
)