package com.translator.uzbek.english.dictionary.android.core.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.translator.uzbek.english.dictionary.core.extensions.tryCatch
import java.util.Locale

@Composable
fun rememberTtsHelper(): TTSHelper {
    val context = LocalContext.current
    return remember { TTSHelper(context) }
}

class TTSHelper(context: Context) {

    private var textToSpeech: TextToSpeech? = null

    init {
        tryCatch {
            textToSpeech = TextToSpeech(context) { status ->
                if (status != TextToSpeech.ERROR) {
                    textToSpeech?.language = Locale.UK
                }
            }
        }
    }

    fun speak(text: String) {
        tryCatch {
            val utteranceId = this.hashCode().toString() + ""
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        }
    }
}