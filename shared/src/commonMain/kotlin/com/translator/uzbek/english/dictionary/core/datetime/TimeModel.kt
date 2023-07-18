package com.translator.uzbek.english.dictionary.core.datetime

import com.translator.uzbek.english.dictionary.core.extensions.az

data class TimeModel(
    val hour: Int,
    val minute: Int
) {
    override fun toString(): String = "${hour.az()}:${minute.az()}"
}
