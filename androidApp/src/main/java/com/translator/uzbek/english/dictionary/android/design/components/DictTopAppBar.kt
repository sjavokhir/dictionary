package com.translator.uzbek.english.dictionary.android.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.design.theme.DividerColor

@Composable
fun DictTopAppBar(
    title: String,
    onNavigateUp: (() -> Unit)? = null
) {
    if (onNavigateUp != null) {
        DictTopAppBarContent(
            title = title,
            onNavigateUp = onNavigateUp
        )
    } else {
        DictTopAppBarContent(title = title)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DictTopAppBarContent(
    title: String,
    onNavigateUp: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .windowInsetsPadding(TopAppBarDefaults.windowInsets)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            if (onNavigateUp != null) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = onNavigateUp
                ) {
                    DictIcon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = DividerColor
        )
    }
}