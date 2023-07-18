import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

android {
    namespace = "com.translator.uzbek.english.dictionary.android"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.translator.uzbek.english.dictionary.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        val keystorePropertiesFile = rootProject.file("key.properties")
        val keystoreProperties = Properties()

        if (keystorePropertiesFile.exists()) {
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
        }

        create("release") {
            keyAlias = (keystoreProperties["keyAlias"] as? String).orEmpty()
            keyPassword = (keystoreProperties["keyPassword"] as? String).orEmpty()
            storeFile = file((keystoreProperties["storeFile"] as? String).orEmpty())
            storePassword = (keystoreProperties["storePassword"] as? String).orEmpty()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    kotlin {
        jvmToolchain(17)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    applicationVariants.all {
        outputs
            .map { it as BaseVariantOutputImpl }
            .forEach { output ->
                output.outputFileName = "uzbek-english-dictionary-$versionName.$versionCode.apk"
            }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":wheel-picker"))

    implementation(libs.kotlin.coroutines)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.billing)

    implementation(libs.compose.activity)
    implementation(libs.compose.lifecycle)
    implementation(libs.compose.runtime)
    implementation(libs.compose.compiler)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.accompanist.permissions)

    implementation(libs.google.material)
    implementation(libs.google.ps.ads)
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)

    implementation(libs.navigation.core)
    ksp(libs.navigation.ksp)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(libs.kmm.viewmodel)
}