package com.translator.uzbek.english.dictionary.android.presentation.settings.faq

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.core.helpers.StringRes
import com.translator.uzbek.english.dictionary.data.model.common.FaqModel
import com.translator.uzbek.english.dictionary.presentation.faq.FaqState
import com.translator.uzbek.english.dictionary.presentation.faq.FaqViewModel

@Destination
@Composable
fun FaqScreen(
    viewModel: FaqViewModel = viewModel(),
    navigator: DestinationsNavigator,
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    DictContainer(
        title = strings.faq,
        onNavigateUp = navigator::navigateUp
    ) {
        FaqScreenContent(state = state)
    }
}

@Composable
private fun FaqScreenContent(state: FaqState) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        items(state.faq) { faq ->
            FaqItem(faq)
        }
    }
}

@Composable
private fun FaqItem(faq: FaqModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = faq.question,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = faq.answer,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline
        )
    }
}