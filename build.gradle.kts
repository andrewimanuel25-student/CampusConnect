// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.compose.multiplatform) apply false

    id("com.google.dagger.hilt.android") version "2.60.1" apply false //Modul 4 dari AI
    id("com.google.devtools.ksp") version "2.3.12" apply false //Modul 4 dari AI
}