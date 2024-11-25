package com.noxinfinity.pdate.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.data.models.profile.bioChips
import com.noxinfinity.pdate.data.models.profile.sampleChipList
import com.noxinfinity.pdate.ui.screens.profile.components.ProfileBio
import com.noxinfinity.pdate.ui.screens.profile.components.ProfileFavorites
import com.noxinfinity.pdate.ui.screens.profile.components.ProfileHeader
import com.noxinfinity.pdate.ui.screens.profile.components.ProfilePhotos
import com.noxinfinity.pdate.ui.screens.theme.DatingApplicationTheme
import com.noxinfinity.pdate.utils.heightPadding


@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 18.dp
            )
            .verticalScroll(rememberScrollState())
    ) {

        ProfileHeader(
            image = R.drawable.default_avatar,
            name = "John Doe",
            description = "Passionate about art and storytelling",
            isVerified = true
        )
        12.heightPadding()
        ProfileBio(
            bioList = sampleChipList,
            modifier = Modifier
                .fillMaxWidth(),
            bioText = "A passionate enthusiast of digital art and anime, eager to share unique creations and captivating stories. Always exploring new artistic realms and connecting with others who share the same love for visual storytellin"
        )

        10.heightPadding()

        ProfileFavorites(
            favoriteChips = bioChips,
        )

        10.heightPadding()

        ProfilePhotos(
            photos = listOf(
                R.drawable.default_avatar,
                R.drawable.default_avatar,
                R.drawable.default_avatar,
                R.drawable.default_avatar,
                R.drawable.default_avatar,
                R.drawable.default_avatar,
            )
        )
    }
}


@Preview
@Composable
fun ProfilePreview() {
    DatingApplicationTheme {
        ProfileScreen()
    }
}
