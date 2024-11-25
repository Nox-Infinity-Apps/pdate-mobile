package com.noxinfinity.pdate.data.models.home

import androidx.annotation.DrawableRes

data class ProfileData(
    val name: String,
    val age: Int,
    val description: String,
    @DrawableRes val imageRes: Int
)