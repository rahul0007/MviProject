package org.example.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun UserImage(
    url: String,
    modifier: Modifier = Modifier
)