package com.translator.uzbek.english.dictionary.android.presentation.dictionary

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.core.extensions.clickableSingle
import com.translator.uzbek.english.dictionary.android.core.extensions.defaultPadding
import com.translator.uzbek.english.dictionary.android.design.components.ConfirmationDialog
import com.translator.uzbek.english.dictionary.android.design.components.DictIcon
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.android.presentation.settings.DividerContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryActionsSheet(
    strings: StringResources,
    show: Boolean,
    isDefault: Boolean,
    onDismiss: () -> Unit,
    onReset: () -> Unit,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
    onClear: () -> Unit,
) {
    var showResetDialog by remember { mutableStateOf(false) }
    var showRemoveDialog by remember { mutableStateOf(false) }
    var showClearDialog by remember { mutableStateOf(false) }

    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            tonalElevation = 0.dp
        ) {
            DictionaryActionsContent(
                isDefault = isDefault,
                onResetProgress = {
                    showResetDialog = true
                },
                onEdit = {
                    onEdit()
                    onDismiss()
                },
                onRemove = {
                    showRemoveDialog = true
                },
                onClear = {
                    showClearDialog = true
                }
            )
        }
    }

    if (showResetDialog) {
        ConfirmationDialog(
            message = strings.confirmationResetProgress,
            onConfirm = {
                onReset()
                onDismiss()

                showResetDialog = false
            },
            onDismiss = {
                showResetDialog = false
            }
        )
    }

    if (showRemoveDialog) {
        ConfirmationDialog(
            message = strings.confirmationRemoveDictionary,
            onConfirm = {
                onRemove()
                onDismiss()

                showRemoveDialog = false
            },
            onDismiss = {
                showRemoveDialog = false
            }
        )
    }

    if (showClearDialog) {
        ConfirmationDialog(
            message = strings.confirmationClearDictionary,
            onConfirm = {
                onClear()
                onDismiss()

                showClearDialog = false
            },
            onDismiss = {
                showClearDialog = false
            }
        )
    }
}

@Composable
private fun DictionaryActionsContent(
    isDefault: Boolean,
    onResetProgress: () -> Unit,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
    onClear: () -> Unit,
) {
    val strings = LocalStrings.current

    Column {
        Text(
            text = strings.dictionaryActions,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        )

        ActionItemContent(
            text = strings.resetProgress,
            icon = R.drawable.ic_reset,
            onClick = onResetProgress
        )

        if (!isDefault) {
            DividerContent()

            ActionItemContent(
                text = strings.editDictionary,
                icon = R.drawable.ic_edit,
                onClick = onEdit
            )

            DividerContent()

            ActionItemContent(
                text = strings.removeThisDictionary,
                icon = R.drawable.ic_delete,
                onClick = onRemove
            )

            DividerContent()

            ActionItemContent(
                text = strings.clearThisDictionary,
                icon = R.drawable.ic_clear_all,
                onClick = onClear
            )
        }
    }
}

@Composable
fun ActionItemContent(
    text: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle(onClick = onClick)
            .defaultPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DictIcon(
            painter = painterResource(id = icon),
            color = MaterialTheme.colorScheme.outline
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
        )
    }
}