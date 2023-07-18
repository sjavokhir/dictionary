package com.translator.uzbek.english.dictionary.core.datetime

data class TimeModel(
    val hour: Int,
    val minute: Int
) {
    override fun toString(): String = "$hour:$minute"
}
