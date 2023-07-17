package com.translator.uzbek.english.dictionary.android.design.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DictIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier,
        tint = color
    )
}

@Composable
fun DictIcon(
    painter: Painter,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Icon(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        tint = color
    )
}