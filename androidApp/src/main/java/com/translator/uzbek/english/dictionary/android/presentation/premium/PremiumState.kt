package com.translator.uzbek.english.dictionary.android.presentation.premium

import com.translator.uzbek.english.dictionary.android.core.billing.ProductDetailsModel
import com.translator.uzbek.english.dictionary.android.core.billing.Subscriptions

data class PremiumState(
    val productDetails: List<ProductDetailsModel> = emptyList(),
    val selectedProductId: String = Subscriptions.Monthly.productId
)
