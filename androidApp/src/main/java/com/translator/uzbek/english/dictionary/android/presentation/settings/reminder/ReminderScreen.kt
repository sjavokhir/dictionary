package com.translator.uzbek.english.dictionary.android.presentation.settings.reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictFilledButton
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.presentation.reminder.ReminderState
import com.translator.uzbek.english.dictionary.presentation.reminder.ReminderViewModel

@Destination
@Composable
fun ReminderScreen(
    viewModel: ReminderViewModel = viewModel(),
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Int>
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    DictContainer(
        title = strings.reminder,
        onNavigateUp = navigator::navigateUp
    ) {
        ReminderScreeenContent(
            strings = strings,
            state = state
        ) {

        }
    }
}

@Composable
private fun ReminderScreeenContent(
    strings: StringResources,
    state: ReminderState,
    onSave: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item {
            DictFilledButton(
                text = strings.save,
                onClick = onSave
            )
        }
    }
}