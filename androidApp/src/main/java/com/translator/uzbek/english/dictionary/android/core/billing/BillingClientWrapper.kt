package com.translator.uzbek.english.dictionary.android.core.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BillingClientWrapper(
    context: Context
) : PurchasesUpdatedListener, ProductDetailsResponseListener {

    private val productDetailsData = MutableStateFlow<List<ProductDetailsModel>>(emptyList())
    val productDetails = productDetailsData.asStateFlow()

    private val hasAcknowledgedData = MutableStateFlow<Boolean?>(null)
    val hasAcknowledged = hasAcknowledgedData.asStateFlow()

    private val isPurchaseAcknowledgedData = MutableStateFlow(false)
    val isPurchaseAcknowledged = isPurchaseAcknowledgedData.asStateFlow()

    private val isLoadingData = MutableStateFlow(false)
    val isLoading = isLoadingData.asStateFlow()

    private val billingClient = BillingClient.newBuilder(context)
        .setListener(this)
        .enablePendingPurchases()
        .build()

    override fun onProductDetailsResponse(
        billingResult: BillingResult,
        productDetailsList: MutableList<ProductDetails>
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            productDetailsData.value = productDetailsList.map {
                ProductDetailsModel(
                    productId = it.productId,
                    name = it.name,
                    description = it.description,
                    formattedPrice = it.subscriptionOfferDetails
                        ?.firstOrNull()
                        ?.pricingPhases
                        ?.pricingPhaseList
                        ?.firstOrNull()
                        ?.formattedPrice ?: "",
                    offerToken = it.subscriptionOfferDetails
                        ?.firstOrNull()
                        ?.offerToken ?: "",
                    productDetails = it
                )
            }
        }
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: List<Purchase>?
    ) {
        isLoadingData.value = true

        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK
            && !purchases.isNullOrEmpty()
        ) {
            purchases.forEach {
                acknowledgePurchases(it)
            }
        } else {
            isLoadingData.value = false
        }
    }

    fun startBillingConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    queryPurchases()
                    queryProductDetails()
                }
            }

            override fun onBillingServiceDisconnected() {
                startBillingConnection()
            }
        })
    }

    fun queryPurchases() {
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        ) { billingResult, purchaseList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                hasAcknowledgedData.value = purchaseList
                    .any { purchase -> purchase.isAcknowledged }
            }
        }
    }

    private fun queryProductDetails() {
        val params = QueryProductDetailsParams.newBuilder()
        val productList = mutableListOf<QueryProductDetailsParams.Product>()

        for (product in LIST_OF_PRODUCTS) {
            productList.add(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(product)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build()
            )
        }

        params.setProductList(productList).let { productDetailsParams ->
            billingClient.queryProductDetailsAsync(productDetailsParams.build(), this)
        }
    }

    fun launchBillingFlow(activity: Activity, params: BillingFlowParams) {
        billingClient.launchBillingFlow(activity, params)
    }

    private fun acknowledgePurchases(purchase: Purchase) {
        if (!purchase.isAcknowledged) {
            val params = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

            billingClient.acknowledgePurchase(params) { _ ->
                isPurchaseAcknowledgedData.value = true
            }
        } else {
            isLoadingData.value = false
        }
    }

    fun terminateBillingConnection() {
        billingClient.endConnection()
    }

    companion object {
        private val LIST_OF_PRODUCTS = listOf(
            Subscriptions.Anually.productId,
            Subscriptions.Monthly.productId,
            Subscriptions.Weekly.productId
        )
    }
}