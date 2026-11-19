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

        // For com.android.kotlin.multiplatform.library, the instrumented test source set is 'androidDeviceTest'
        val androidDeviceTest by getting {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.junit)
            }
        }
        
        // The unit test source set is 'androidHostTest'
        val androidHostTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
