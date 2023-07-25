package com.translator.uzbek.english.dictionary.android.presentation.settings.appLanguage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
import com.translator.uzbek.english.dictionary.data.model.mode.LanguageMode

@Destination
@Composable
fun AppLanguageScreen(
    language: LanguageMode,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<LanguageMode>
) {
    val strings = LocalStrings.current

    DictContainer(
        title = strings.appLanguage,
        onNavigateUp = navigator::navigateUp
    ) {
        AppLanguageScreenContent(
            language = language,
            onClick = resultNavigator::navigateBack
        )
    }
}

@Composable
private fun AppLanguageScreenContent(
    language: LanguageMode,
    onClick: (LanguageMode) -> Unit
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
            items(LanguageMode.values()) {
                LanguageItem(
                    language = it,
                    selectedLanguage = language,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun LanguageItem(
    language: LanguageMode,
    selectedLanguage: LanguageMode,
    onClick: (LanguageMode) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle {
                    onClick(language)
                }
                .defaultPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(
                    id = when (language) {
                        LanguageMode.Uzbek -> R.drawable.ic_uzbekistan
                        LanguageMode.English -> R.drawable.ic_english
                    }
                ),
                contentDescription = language.language,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = language.language,
                style = MaterialTheme.typography.bodyLarge,
                color = if (language == selectedLanguage) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
                fontWeight = if (language == selectedLanguage) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.Normal
                },
                modifier = Modifier.weight(1f)
            )

            if (language == selectedLanguage) {
                DictIcon(
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (language != LanguageMode.English) {
            DividerContent(
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}