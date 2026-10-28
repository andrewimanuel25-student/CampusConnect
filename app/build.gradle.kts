plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    //id("com.android.application") //Modul 4 dari AI
    //id("org.jetbrains.kotlin.android") //Modul 4 dari AI
    id("com.google.dagger.hilt.android") //Modul 4 dari AI
    id("com.google.devtools.ksp") //Modul 4 dari AI
}

android {
    namespace = "com.kharismastudio.campusconnect"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.kharismastudio.campusconnect"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":shared")) //dari modul 2
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation("com.google.dagger:hilt-android:2.60.1") //Modul 4 dari AI
    ksp("com.google.dagger:hilt-compiler:2.60.1") //Modul 4 dari AI
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0") //Modul 4 dari AI
}