package com.translator.uzbek.english.dictionary.shared

import kotlinx.coroutines.Dispatchers
import platform.CoreFoundation.CFUUIDCreate
import platform.CoreFoundation.CFUUIDCreateString
import platform.Foundation.CFBridgingRelease

actual val appUrl: String
    get() = ""

actual val appVersion: String
    get() = "1.0.1"

actual val deviceVersion: String
    get() = ""

actual fun randomUUID(): String =
    CFBridgingRelease(CFUUIDCreateString(null, CFUUIDCreate(null))) as String

actual val mainDispatcher = Dispatchers.Main
actual val ioDispatcher = Dispatchers.Default