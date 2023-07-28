package com.translator.uzbek.english.dictionary.android.presentation.settings.reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.core.extensions.clickableSingle
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictFilledButton
import com.translator.uzbek.english.dictionary.android.design.components.DictIcon
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.design.mapper.weekShortName
import com.translator.uzbek.english.dictionary.presentation.reminder.ReminderEvent
import com.translator.uzbek.english.dictionary.presentation.reminder.ReminderState
import com.translator.uzbek.english.dictionary.presentation.reminder.ReminderViewModel
import uz.javokhir.picker.WheelTimePicker
import java.io.Serializable
import java.time.LocalTime

data class ReminderResult(
    val hour: Int,
    val minute: Int,
    val weekdays: String
) : Serializable

@Destination
@Composable
fun ReminderScreen(
    viewModel: ReminderViewModel = viewModel(),
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<ReminderResult>
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    DictContainer(
        title = strings.reminder,
        onNavigateUp = navigator::navigateUp
    ) {
        ReminderScreeenContent(
            strings = strings,
            state = state,
            onEvent = viewModel::onEvent
        ) {
            resultNavigator.navigateBack(
                ReminderResult(
                    hour = state.hour,
                    minute = state.minute,
                    weekdays = state.weekdays.joinToString(",")
                )
            )
        }
    }
}

@Composable
private fun ReminderScreeenContent(
    strings: StringResources,
    state: ReminderState,
    onEvent: (ReminderEvent) -> Unit,
    onSave: () -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item {
            WheelTimePicker(
                startTime = LocalTime.of(state.hour, state.minute)
            ) {
                onEvent(ReminderEvent.ChangeHourMinute(it.hour, it.minute))
            }
        }
        item {
            WeekdaysContent(strings, state, onEvent)
        }
        item {
            DictFilledButton(
                text = strings.save,
                onClick = onSave
            )
        }
    }
}

@Composable
private fun WeekdaysContent(
    strings: StringResources,
    state: ReminderState,
    onEvent: (ReminderEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = strings.repeat,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = strings.selectAll,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickableSingle(
                        hasIndication = false,
                        onClick = {
                            onEvent(ReminderEvent.SelectAll)
                        }
                    )
                    .padding(vertical = 10.dp)
            )
        }

        Row {
            state.weekdays.forEachIndexed { position, selected ->
                WeekdayContent(
                    position = position,
                    selected = selected,
                    onClick = {
                        onEvent(ReminderEvent.SelectWeekday(position, selected))
                    }
                )
            }
        }
    }
}

@Composable
private fun RowScope.WeekdayContent(
    position: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickableSingle(
                hasIndication = false,
                onClick = onClick
            )
            .padding(horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DictIcon(
            painter = if (selected) {
                painterResource(id = R.drawable.ic_check_circle)
            } else {
                painterResource(id = R.drawable.ic_check_empty)
            },
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline.copy(0.2f)
            },
            modifier = Modifier.size(30.dp)
        )

        Text(
            text = position.weekShortName(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
