package com.translator.uzbek.english.dictionary.shared

import com.translator.uzbek.english.dictionary.data.model.mode.LanguageMode
import com.translator.uzbek.english.dictionary.data.model.mode.ThemeMode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object EventChannel {

    private val channel = Channel<Event>()

    fun sendEvent(event: Event) {
        channel.trySend(event)
    }

    fun receiveEvent(): Flow<Event> {
        return channel.receiveAsFlow()
    }
}

sealed class Event {
    data class LanguageChanged(val language: LanguageMode) : Event()
    data class ThemeModeChanged(val themeMode: ThemeMode) : Event()

    object Idle : Event()
}