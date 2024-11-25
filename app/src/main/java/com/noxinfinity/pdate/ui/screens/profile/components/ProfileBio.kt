package com.noxinfinity.pdate.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.data.models.profile.BioChip
import com.noxinfinity.pdate.ui.screens.common.AppButton
import com.noxinfinity.pdate.utils.heightPadding
import com.noxinfinity.pdate.utils.widthPadding

@Composable
fun ProfileBio(modifier: Modifier = Modifier, bioList: List<BioChip>, bioText: String) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            AppButton(
                icon = bioList[0].chipIcon,
                borderRadius = 8,
                modifier = Modifier.weight(1f),
                verticalPadding = 12,
                text = {
                    Text(
                        text = bioList[0].chipName,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            )
            12.widthPadding()
            AppButton(
                icon = bioList[1].chipIcon,
                modifier = Modifier.weight(1f),
                borderRadius = 8,
                verticalPadding = 12,
                text = {
                    Text(
                        text = bioList[1].chipName,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            )
        }

        10.heightPadding()

        Text(
            text = bioText,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}