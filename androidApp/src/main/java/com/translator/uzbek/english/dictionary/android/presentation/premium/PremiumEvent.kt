package com.translator.uzbek.english.dictionary.android.presentation.premium

import android.app.Activity

sealed class PremiumEvent {
    data class SelectProduct(val price: String) : PremiumEvent()
    data class Buy(val activity: Activity) : PremiumEvent()
}