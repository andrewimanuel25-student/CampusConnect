plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.android.lint)
}

kotlin {
    androidLibrary {
        namespace = "com.kharismastudio.shared"
        compileSdk = 35
        minSdk = 29
    }
    
    jvm("desktop")

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
            }
        }

        androidMain {
            dependencies {
            }
        }

        // In com.android.kotlin.multiplatform.library, instrumented tests use 'androidDeviceTest'
        val androidDeviceTest by getting {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.junit)
            }
        }

        // Unit tests use 'androidHostTest'
        val androidHostTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
