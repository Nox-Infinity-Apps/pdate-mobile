package com.noxinfinity.pdate.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.data.models.profile.BioChip

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileFavorites(modifier: Modifier = Modifier, favoriteChips: List<BioChip>) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        favoriteChips.forEach { bioChip ->
            AssistChip(
                onClick = { /* Handle click */ },
                label = { Text(bioChip.chipName) },
                leadingIcon = {
                    bioChip.chipIcon()
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
