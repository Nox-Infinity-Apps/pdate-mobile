package com.noxinfinity.pdate.data.models.profile

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BirthdayCake
import compose.icons.fontawesomeicons.solid.Film
import compose.icons.fontawesomeicons.solid.LocationArrow
import compose.icons.fontawesomeicons.solid.Music
import compose.icons.fontawesomeicons.solid.PaintBrush
import compose.icons.fontawesomeicons.solid.ShoppingCart
import compose.icons.fontawesomeicons.solid.Star
import compose.icons.fontawesomeicons.solid.SwimmingPool
import compose.icons.fontawesomeicons.solid.Utensils

data class BioChip(
    val name: String,
    val icon: String? = null,
    val id: Int,
)

