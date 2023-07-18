package com.translator.uzbek.english.dictionary.android.presentation.settings.feedback

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.core.extensions.openUrl
import com.translator.uzbek.english.dictionary.android.core.extensions.sendMail
import com.translator.uzbek.english.dictionary.android.design.components.DictContainer
import com.translator.uzbek.english.dictionary.android.design.components.DictFilledButton
import com.translator.uzbek.english.dictionary.android.design.components.DictOutlinedButton
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.design.localization.StringResources
import com.translator.uzbek.english.dictionary.core.helpers.Constants
import com.translator.uzbek.english.dictionary.shared.appVersion
import com.translator.uzbek.english.dictionary.shared.deviceVersion

@Destination
@Composable
fun FeedbackScreen(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val strings = LocalStrings.current

    DictContainer(
        title = strings.feedback,
        onNavigateUp = navigator::navigateUp
    ) {
        FeedbackScreenContent(
            strings = strings,
            onSendEmail = {
                context.sendMail(
                    email = Constants.email,
                    subject = "Feedback regarding ${strings.appName} [$appVersion - $deviceVersion]"
                )
            },
            onOpenUrl = {
                context.openUrl(Constants.supportBot)
            }
        )
    }
}

@Composable
private fun FeedbackScreenContent(
    strings: StringResources,
    onSendEmail: () -> Unit,
    onOpenUrl: () -> Unit,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.ic_feedback_illustration),
                contentDescription = null
            )
        }
        item {
            Text(
                text = strings.helpUsImprove,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = strings.feedbackDescription,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Start
            )
        }
        item {
            DictFilledButton(
                text = strings.sendUs,
                onClick = onSendEmail
            )
        }
        item {
            DictOutlinedButton(
                text = strings.telegramBot,
                onClick = onOpenUrl
            )
        }
    }
}