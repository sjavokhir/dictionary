package com.translator.uzbek.english.dictionary.android.presentation.settings.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.core.extensions.clickableSingle
import com.translator.uzbek.english.dictionary.android.core.extensions.defaultPadding
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictIcon
import com.translator.uzbek.english.dictionary.android.design.components.DividerContent
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.localized
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
import com.translator.uzbek.english.dictionary.data.model.mode.ThemeMode

@Destination
@Composable
fun ThemeModeScreen(
    themeMode: ThemeMode,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<ThemeMode>
) {
    val strings = LocalStrings.current

    DictContainer(
        title = strings.theme,
        onNavigateUp = navigator::navigateUp
    ) {
        ThemeModeScreenContent(
            themeMode = themeMode,
            onClick = resultNavigator::navigateBack
        )
    }
}

@Composable
private fun ThemeModeScreenContent(
    themeMode: ThemeMode,
    onClick: (ThemeMode) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WindowBackground),
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(20.dp)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = DividerColor,
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(ThemeMode.values()) {
                ThemeModeItem(
                    title = it.localized(),
                    themeMode = it,
                    selectedThemeMode = themeMode,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun ThemeModeItem(
    title: String,
    themeMode: ThemeMode,
    selectedThemeMode: ThemeMode,
    onClick: (ThemeMode) -> Unit
) {
    val isSelected = remember(selectedThemeMode) { themeMode == selectedThemeMode }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle {
                    onClick(themeMode)
                }
                .defaultPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
                fontWeight = if (isSelected) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.Normal
                },
                modifier = Modifier.weight(1f)
            )

            DictIcon(
                painter = painterResource(id = R.drawable.ic_check_circle),
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                }
            )
        }

        if (themeMode != ThemeMode.Dark) {
            DividerContent(
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}