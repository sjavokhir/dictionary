package com.translator.uzbek.english.dictionary.android.presentation.settings.dailyGoal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictFilledButton
import com.translator.uzbek.english.dictionary.android.design.components.DictTextField
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources

@Destination
@Composable
fun DailyGoalScreen(
    dailyGoal: Int,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Int>
) {
    val strings = LocalStrings.current

    DictContainer(
        title = strings.dailyGoal,
        onNavigateUp = navigator::navigateUp
    ) {
        DailyGoalScreenContent(
            strings = strings,
            dailyGoal = dailyGoal,
            onSave = resultNavigator::navigateBack
        )
    }
}

@Composable
private fun DailyGoalScreenContent(
    strings: StringResources,
    dailyGoal: Int,
    onSave: (Int) -> Unit
) {
    var dailyGoalValue by remember {
        mutableStateOf(if (dailyGoal == 0) "" else dailyGoal.toString())
    }
    val dailyGoalText = remember(dailyGoalValue) {
        val goal = dailyGoalValue.toIntOrNull() ?: 0

        if (goal == 0) {
            strings.notSet
        } else {
            strings.youWillLearn(goal * 30)
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item {
            Text(
                text = strings.dailyGoalDescription,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        item {
            DictTextField(
                value = dailyGoalValue,
                onValueChange = {
                    if (it.length <= 3) {
                        dailyGoalValue = it
                    }
                },
                placeholder = strings.notSet,
                hint = strings.dailyGoal,
                keyboardType = KeyboardType.Number
            )
        }
        item {
            Text(
                text = dailyGoalText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        item {
            DictFilledButton(text = strings.save) {
                onSave(dailyGoalValue.toIntOrNull() ?: 0)
            }
        }
    }
}