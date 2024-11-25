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
    val chipName: String,
    val chipIcon: @Composable () -> Unit
)

val sampleChipList = listOf(
    BioChip("Bogor, Jawa Barat") {
        Icon(
            imageVector = FontAwesomeIcons.Solid.LocationArrow,
            contentDescription = "Location",
            tint = Color.Red,
            modifier = Modifier.size(16.dp)
        )
    },
    BioChip("5 Common Interest") {
        Icon(
            imageVector = FontAwesomeIcons.Solid.Star,
            contentDescription = "Favorite",
            tint = Color.Yellow,
            modifier = Modifier.size(16.dp)
        )
    },
)

val bioChips = listOf(
    BioChip("Anime") {
        Icon(
            imageVector = FontAwesomeIcons.Solid.Film,
            contentDescription = "Film",
            tint = Color.Black,
            modifier = Modifier.size(18.dp)
        )
    },
    BioChip("Music") {
        Icon(
            imageVector = FontAwesomeIcons.Solid.Music,
            contentDescription = "Music",
            tint = Color.Black,
            modifier = Modifier.size(18.dp)
        )
    },
    BioChip("Digital Art") {
        Icon(
            imageVector = FontAwesomeIcons.Solid.PaintBrush,
            contentDescription = "PaintBrush",
            tint = Color.Red,
            modifier = Modifier.size(18.dp)
        )
    },
    BioChip("Cake") {
        Icon(
            imageVector = FontAwesomeIcons.Solid.BirthdayCake,
            contentDescription = "BirthdayCake",
            tint = Color.Cyan,
            modifier = Modifier.size(18.dp)
        )
    },
    BioChip("Shopping") {
        Icon(
            imageVector = FontAwesomeIcons.Solid.ShoppingCart,
            contentDescription = "ShoppingCart",
            tint = Color.Cyan,
            modifier = Modifier.size(18.dp)
        )
    },
    BioChip("Swimming") {
        Icon(
            imageVector = FontAwesomeIcons.Solid.SwimmingPool,
            contentDescription = "SwimmingPool",
            tint = Color.Blue,
            modifier = Modifier.size(18.dp)
        )
    },
    BioChip("Ramen") {
        Icon(
            imageVector = FontAwesomeIcons.Solid.Utensils,
            contentDescription = "Utensils",
            tint = Color.Black,
            modifier = Modifier.size(18.dp)
        )
    }
)