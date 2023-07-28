package com.translator.uzbek.english.dictionary.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.translator.uzbek.english.dictionary.android.core.helpers.AdUnits
import com.translator.uzbek.english.dictionary.android.design.components.DictBackground
import com.translator.uzbek.english.dictionary.android.design.theme.DictTheme
import com.translator.uzbek.english.dictionary.android.navigation.DictApp
import com.translator.uzbek.english.dictionary.data.datastore.AppStore
import com.translator.uzbek.english.dictionary.data.model.mode.LanguageMode
import com.translator.uzbek.english.dictionary.data.model.mode.ThemeMode
import com.translator.uzbek.english.dictionary.shared.Event
import com.translator.uzbek.english.dictionary.shared.EventChannel
import org.koin.android.ext.android.inject
import kotlin.random.Random

class DictionaryActivity : ComponentActivity() {

//    private val viewModel by viewModels<PremiumViewModel>()

    private val appStore by inject<AppStore>()

    private var mInterstitialAd: InterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null

    private var isAdShowed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        MobileAds.initialize(this)
        loadRewardedAd()
        loadInterstitialAd()

        setContent {
//            val hasAcknowledged = viewModel.hasAcknowledged.collectAsStateWithLifecycle().value

            var language by remember { mutableStateOf(appStore.getAppLanguage()) }
            var themeMode by remember { mutableStateOf(appStore.getThemeMode()) }

            when (
                val event = EventChannel.receiveEvent().collectAsStateWithLifecycle(
                    initialValue = Event.Idle,
                    lifecycle = lifecycle
                ).value
            ) {
                is Event.LanguageChanged -> language = event.language
                is Event.ThemeModeChanged -> themeMode = event.themeMode
                else -> {}
            }

            DictApp(
                hasSubscription = false,
                language = language,
                themeMode = themeMode,
            )

//            if (hasAcknowledged != null) {
//                DictApp(
//                    hasSubscription = hasAcknowledged,
//                    language = language,
//                    themeMode = themeMode,
//                )
//            } else {
//                BillingLoadingContent(
//                    language = language,
//                    themeMode = themeMode
//                )
//            }
        }
    }

    override fun onDestroy() {
        removeInterstitialAd()
        removeRewardedAd()
        super.onDestroy()
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun BillingLoadingContent(
        language: LanguageMode,
        themeMode: ThemeMode,
    ) {
        DictTheme(
            hasSubscription = false,
            language = language,
            themeMode = themeMode
        ) {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ) { padding ->
                DictBackground(
                    modifier = Modifier
                        .padding(padding)
                        .consumeWindowInsets(padding),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(MaterialTheme.shapes.large),
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }
        }
    }

    fun showAds(hasSubscription: Boolean) {
        if (!hasSubscription && !isAdShowed) {
            if (Random.nextBoolean()) {
                showRewardedAd()
            } else {
                showInterstitialAd()
            }
        }
    }

    private fun loadInterstitialAd() {
        InterstitialAd.load(
            this,
            AdUnits.interstitialId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(e: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

    private fun loadRewardedAd() {
        RewardedAd.load(
            this,
            AdUnits.rewardedId,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(e: LoadAdError) {
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd

                    showRewardedAd()
                }
            })
    }

    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mInterstitialAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null

                    loadInterstitialAd()
                }

                override fun onAdShowedFullScreenContent() {
                    isAdShowed = true
                }
            }
            mInterstitialAd?.show(this)
        }
    }

    private fun showRewardedAd() {
        if (mRewardedAd != null) {
            mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mRewardedAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    mRewardedAd = null

//                    loadRewardedAd()
                }

                override fun onAdShowedFullScreenContent() {
                    isAdShowed = true
                }
            }
            mRewardedAd?.show(this) {}
        }
    }

    private fun removeInterstitialAd() {
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd = null
    }

    private fun removeRewardedAd() {
        mRewardedAd?.fullScreenContentCallback = null
        mRewardedAd = null
    }
}