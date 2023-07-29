package com.translator.uzbek.english.dictionary.android.presentation.dictionaryWords

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.design.components.ConfirmationDialog
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.presentation.dictionary.ActionItemContent
import com.translator.uzbek.english.dictionary.android.presentation.settings.DividerContent
import com.translator.uzbek.english.dictionary.data.database.model.WordModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordActionsSheet(
    strings: StringResources,
    selectedWord: WordModel?,
    onDismiss: () -> Unit,
    onStatus: (String, WordModel.WordStatus) -> Unit,
    onCopy: (() -> Unit)? = null,
    onEdit: (WordModel) -> Unit,
    onRemove: (String) -> Unit,
) {
    var showRemoveDialog by remember { mutableStateOf(false) }

    if (selectedWord != null) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            tonalElevation = 0.dp
        ) {
            WordActionsContent(
                model = selectedWord,
                onReset = {
                    onStatus(selectedWord.id, WordModel.WordStatus.New)
                    onDismiss()
                },
                onLearned = {
                    onStatus(selectedWord.id, WordModel.WordStatus.Learned)
                    onDismiss()
                },
                onLearning = {
                    onStatus(selectedWord.id, WordModel.WordStatus.Learning)
                    onDismiss()
                },
                onCopy = onCopy,
                onEdit = {
                    onEdit(selectedWord)
                    onDismiss()
                },
                onRemove = {
                    showRemoveDialog = true
                },
            )
        }
    }

    if (showRemoveDialog) {
        ConfirmationDialog(
            message = strings.confirmationRemoveWord,
            onConfirm = {
                selectedWord?.let { onRemove(it.id) }
                onDismiss()

                showRemoveDialog = false
            },
            onDismiss = {
                showRemoveDialog = false
            }
        )
    }
}

@Composable
private fun WordActionsContent(
    model: WordModel,
    onReset: () -> Unit,
    onLearned: () -> Unit,
    onLearning: () -> Unit,
    onCopy: (() -> Unit)? = null,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
) {
    val strings = LocalStrings.current

    Column {
        Text(
            text = strings.wordActions,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        )

        if (model.status == WordModel.WordStatus.New) {
            ActionItemContent(
                text = strings.markAsAlreadyKnown,
                icon = R.drawable.ic_mark,
                onClick = onLearned
            )

            DividerContent()

            ActionItemContent(
                text = strings.learning,
                icon = R.drawable.ic_learning,
                onClick = onLearning
            )
        } else {
            ActionItemContent(
                text = strings.resetProgressForThisWord,
                icon = R.drawable.ic_reset,
                onClick = onReset
            )
        }

        DividerContent()

        if (onCopy != null) {
            ActionItemContent(
                text = strings.copyToDictionary,
                icon = R.drawable.ic_word_copy,
                onClick = onCopy
            )
        }

        DividerContent()

        ActionItemContent(
            text = strings.edit,
            icon = R.drawable.ic_edit,
            onClick = onEdit
        )

        DividerContent()

        ActionItemContent(
            text = strings.remove,
            icon = R.drawable.ic_delete,
            onClick = onRemove
        )
    }
}