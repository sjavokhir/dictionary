package com.translator.uzbek.english.dictionary.android.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.spec.Route
import com.translator.uzbek.english.dictionary.android.presentation.appCurrentDestinationAsState
import com.translator.uzbek.english.dictionary.android.presentation.destinations.Destination
import com.translator.uzbek.english.dictionary.android.presentation.startAppDestination

@Composable
fun DictScaffold(
    startRoute: Route,
    navController: NavHostController,
    bottomBar: @Composable (Destination) -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    val destination = navController.appCurrentDestinationAsState().value
        ?: startRoute.startAppDestination

    Scaffold(
        bottomBar = { bottomBar(destination) },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        content = content
    )
}