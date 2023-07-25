package com.translator.uzbek.english.dictionary.android.design.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DictContainer(
    title: String = "",
    onNavigateUp: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    DictBackground {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            DictTopAppBar(title, onNavigateUp)

            content()
        }
    }
}