package com.translator.uzbek.english.dictionary.shared

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

expect val appUrl: String
expect val appVersion: String
expect val deviceVersion: String

expect fun randomUUID(): String

expect val mainDispatcher: MainCoroutineDispatcher
expect val ioDispatcher: CoroutineDispatcher