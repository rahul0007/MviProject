package org.example.project.utils

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
actual fun UserImage(url: String, modifier: Modifier) {
    AsyncImage(
        model = url,
        contentDescription = "User Image",
        modifier = modifier.clip(RoundedCornerShape(12.dp))
    )
}
