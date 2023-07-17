package com.translator.uzbek.english.dictionary.android.core.billing

import com.android.billingclient.api.ProductDetails

data class ProductDetailsModel(
    val productId: String,
    val name: String,
    val description: String,
    val formattedPrice: String,
    val offerToken: String,
    val productDetails: ProductDetails
)