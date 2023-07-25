package com.translator.uzbek.english.dictionary.android.core.extensions

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.defaultPadding() = this.then(
    Modifier.padding(
        horizontal = 16.dp,
        vertical = 15.dp
    )
)