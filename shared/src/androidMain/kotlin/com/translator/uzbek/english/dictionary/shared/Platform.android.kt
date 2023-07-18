package com.translator.uzbek.english.dictionary.shared

import android.os.Build
import kotlinx.coroutines.Dispatchers
import java.util.UUID

actual val appUrl: String
    get() = "https://play.google.com/store/apps/details?id=com.translator.uzbek.english.dictionary.android"

actual val developerUrl: String
    get() = "https://play.google.com/store/apps/dev?id=5105296000562157932"

actual val appVersion: String
    get() = "1.0.1"

actual val deviceVersion: String
    get() = "Android, ${Build.MANUFACTURER} ${Build.MODEL}, ${Build.VERSION.SDK_INT}"

actual fun randomUUID() = UUID.randomUUID().toString()

actual val mainDispatcher = Dispatchers.Main
actual val ioDispatcher = Dispatchers.IO