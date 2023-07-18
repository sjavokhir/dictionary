package com.translator.uzbek.english.dictionary.android.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import com.translator.uzbek.english.dictionary.android.R
import com.translator.uzbek.english.dictionary.android.design.components.DictNavigationBar
import com.translator.uzbek.english.dictionary.android.design.components.DictNavigationBarItem
import com.translator.uzbek.english.dictionary.android.design.localization.LocalStrings
import com.translator.uzbek.english.dictionary.android.presentation.destinations.DictionaryScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.destinations.DirectionDestination
import com.translator.uzbek.english.dictionary.android.presentation.destinations.LearnScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.destinations.SettingsScreenDestination
import com.translator.uzbek.english.dictionary.android.presentation.destinations.StatisticsScreenDestination

@Composable
fun QRBottomBar(
    show: Boolean,
    showAds: Boolean,
    hasSubscription: Boolean,
    navController: NavHostController
) {
    DictNavigationBar(
        show = show,
        showAds = showAds,
        hasSubscription = hasSubscription
    ) {
        bottomBarItems().forEach { destination ->
            val isCurrentDestOnBackStack = navController.isRouteOnBackStack(destination.direction)

            DictNavigationBarItem(
                icon = if (isCurrentDestOnBackStack) {
                    painterResource(id = destination.selectedIcon)
                } else {
                    painterResource(id = destination.unselectedIcon)
                },
                label = destination.label,
                selected = isCurrentDestOnBackStack
            ) {
                navController.bottomNavigateTo(
                    isCurrentDestOnBackStack,
                    destination.direction
                )
            }
        }
    }
}

@Composable
fun bottomBarItems(): List<BottomBarItem> {
    val strings = LocalStrings.current

    return listOf(
        BottomBarItem(
            direction = LearnScreenDestination,
            selectedIcon = R.drawable.ic_learn_selected,
            unselectedIcon = R.drawable.ic_learn_unselected,
            label = strings.learn
        ),
        BottomBarItem(
            direction = DictionaryScreenDestination,
            selectedIcon = R.drawable.ic_dictionary_selected,
            unselectedIcon = R.drawable.ic_dictionary_unselected,
            label = strings.dictionary
        ),
        BottomBarItem(
            direction = StatisticsScreenDestination,
            selectedIcon = R.drawable.ic_statistics_selected,
            unselectedIcon = R.drawable.ic_statistics_unselected,
            label = strings.statistics
        ),
        BottomBarItem(
            direction = SettingsScreenDestination,
            selectedIcon = R.drawable.ic_settings_selected,
            unselectedIcon = R.drawable.ic_settings_unselected,
            label = strings.settings
        )
    )
}

data class BottomBarItem(
    val direction: DirectionDestination,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val label: String
)