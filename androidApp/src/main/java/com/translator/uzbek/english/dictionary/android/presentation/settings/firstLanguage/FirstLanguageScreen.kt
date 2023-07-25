package com.translator.uzbek.english.dictionary.android.presentation.settings.firstLanguage

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
import com.translator.uzbek.english.dictionary.android.design.localization.localized
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor
import com.translator.uzbek.english.dictionary.android.design.theme.WindowBackground
import com.translator.uzbek.english.dictionary.data.model.mode.FirstLanguageMode
import java.io.Serializable

data class FirstLanguageResult(
    val firstLanguage: FirstLanguageMode,
    val type: Type
) : Serializable {

    enum class Type {
        NewWord,
        Repeated
    }
}

@Destination
@Composable
fun FirstLanguageScreen(
    firstLanguage: FirstLanguageMode,
    type: FirstLanguageResult.Type,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<FirstLanguageResult>
) {
    DictContainer(
        onNavigateUp = navigator::navigateUp
    ) {
        FirstLanguageScreenContent(firstLanguage) {
            resultNavigator.navigateBack(FirstLanguageResult(it, type))
        }
    }
}

@Composable
private fun FirstLanguageScreenContent(
    firstLanguage: FirstLanguageMode,
    onClick: (FirstLanguageMode) -> Unit
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
            items(FirstLanguageMode.values()) {
                LanguageItem(
                    firstLanguage = it,
                    selectedFirstLanguage = firstLanguage,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun LanguageItem(
    firstLanguage: FirstLanguageMode,
    selectedFirstLanguage: FirstLanguageMode,
    onClick: (FirstLanguageMode) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle {
                    onClick(firstLanguage)
                }
                .defaultPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = firstLanguage.localized(),
                style = MaterialTheme.typography.bodyLarge,
                color = if (firstLanguage == selectedFirstLanguage) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
                fontWeight = if (firstLanguage == selectedFirstLanguage) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.Normal
                },
                modifier = Modifier.weight(1f)
            )

            if (firstLanguage == selectedFirstLanguage) {
                DictIcon(
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (firstLanguage != FirstLanguageMode.Random) {
            DividerContent(
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}