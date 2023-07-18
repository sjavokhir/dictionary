package com.translator.uzbek.english.dictionary.android.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.core.extensions.clickableSingle

@Composable
fun GoProContent(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.07f))
            .clickableSingle(onClick = onClick)
            .padding(vertical = 12.dp)
            .padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_remove_ads_2),
            contentDescription = null
        )

//        Column {
//            Text(
//                text = strings.goPro,
//                style = MaterialTheme.typography.bodyLarge,
//                fontWeight = FontWeight.SemiBold,
//                color = MaterialTheme.colorScheme.primary
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//            FeatureItem(feature = strings.feature3)
//
//            Spacer(modifier = Modifier.height(4.dp))
//            FeatureItem(feature = strings.feature4)
//        }
    }
}

@Composable
private fun FeatureItem(feature: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DictIcon(
            painter = painterResource(id = R.drawable.ic_check_outline),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = feature,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}