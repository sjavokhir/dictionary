package com.translator.uzbek.english.dictionary.android.core.extensions

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import com.translator.uzbek.english.dictionary.core.extensions.tryCatch
import com.translator.uzbek.english.dictionary.core.helpers.StringRes
import kotlin.system.exitProcess

fun Context.drawableId(name: String): Int? {
    return try {
        resources.getIdentifier(
            name,
            "drawable",
            packageName
        )
    } catch (t: Throwable) {
        null
    }
}

fun Context.findActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

fun Context.restartApp() {
    findActivity()?.let { activity ->
        val intent = activity.intent
        activity.finish()
        activity.startActivity(intent)
    } ?: {
        exitProcess(0)
    }
}

fun Context.toast(message: String?) {
    if (!message.isNullOrEmpty()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.copyToClipboard(text: String) {
    tryCatch {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(StringRes.copiedText, text)
        clipboard.setPrimaryClip(clip)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            toast(StringRes.copiedToClipboard)
        }
    }
}

fun Context.shareText(text: String) {
    tryCatch {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, null))
    }
}

fun Context.openUrl(url: String) {
    tryCatch {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }
}

fun Context.sendMail(
    email: String,
    subject: String = "",
    message: String = ""
) {
    tryCatch {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        startActivity(Intent.createChooser(intent, StringRes.sendEmail))
    }
}