package com.translator.uzbek.english.dictionary.android.presentation.premium

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.BillingFlowParams
import com.translator.uzbek.english.dictionary.android.core.billing.BillingClientWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PremiumViewModel(application: Application) : AndroidViewModel(application) {

    private val billingClient = BillingClientWrapper(application)

    private val stateData = MutableStateFlow(PremiumState())
    val state = stateData.asStateFlow()

    val hasAcknowledged = billingClient.hasAcknowledged
    val isPurchaseAcknowledged = billingClient.isPurchaseAcknowledged
    val isLoading = billingClient.isLoading

    override fun onCleared() {
        billingClient.terminateBillingConnection()
    }

    init {
        billingClient.startBillingConnection()

        collectProductDetails()
    }

    fun onEvent(event: PremiumEvent) {
        when (event) {
            is PremiumEvent.SelectProduct -> onSelectProduct(event.price)
            is PremiumEvent.Buy -> buy(event.activity)
        }
    }

    private fun collectProductDetails() {
        viewModelScope.launch {
            billingClient.productDetails.collectLatest { productDetails ->
                stateData.update { it.copy(productDetails = productDetails) }
            }
        }
    }

    private fun onSelectProduct(price: String) {
        stateData.update { it.copy(selectedProductId = price) }
    }

    private fun buy(activity: Activity) {
        val productDetails = state.value.productDetails
            .firstOrNull { it.productId == state.value.selectedProductId }
            ?.productDetails ?: return

        val offerToken = state.value.productDetails
            .firstOrNull { it.productId == state.value.selectedProductId }
            ?.offerToken ?: return

        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .setOfferToken(offerToken)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        billingClient.launchBillingFlow(
            activity,
            billingFlowParams
        )
    }
}