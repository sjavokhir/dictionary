package com.translator.uzbek.english.dictionary.android.design.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor

@Composable
fun DividerContent(
    modifier: Modifier = Modifier,
    color: Color = DividerColor
) {
    Divider(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp),
        color = color
    )
}