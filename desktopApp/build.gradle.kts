plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting {
            kotlin.srcDirs("src/main/java")
            dependencies {
                implementation(project(":shared"))
                implementation(compose.desktop.currentOs)
                // TAMBAHKAN BARIS INI: Untuk mengaktifkan komponen Text Material 3
                implementation(compose.material3)
            }
        }
    }
}


compose.desktop {
    application {
        mainClass = "com.kharisma.campusconnect.desktopapp.MainKt"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg
            )
            packageName = "CampusConnect"
            packageVersion = "1.0.0"
        }
    }
}
