package com.translator.uzbek.english.dictionary.android.core.billing

enum class Subscriptions(val productId: String) {
    Weekly("subscription_weekly"),
    Monthly("subscription_monthly"),
    Anually("subscription_anually")
}

fun String.caption(): String {
    return when (this) {
        Subscriptions.Weekly.productId -> "/week"
        Subscriptions.Monthly.productId -> "/month"
        Subscriptions.Anually.productId -> "/year"
        else -> this
    }
}