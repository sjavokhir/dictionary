@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.cocoapods).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.google.services).apply(false)
    alias(libs.plugins.google.firebase.crashlytics).apply(false)
    alias(libs.plugins.sqldelight).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}