import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
//    linuxX64() // on Linux
//    mingwX64() // on Windows
//    macosX64() { // on macOS
//        binaries {
//            executable()
//        }
//    }
    androidNativeArm64()
    androidNativeArm32{
        binaries {
            sharedLib("aa", listOf(RELEASE))
        }
    }
    jvm()
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
        }
        jvmMain.dependencies {

        }
        nativeMain.dependencies {  }

    }

}

android {
    namespace = "org.electrolytej.f.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
tasks.withType<Wrapper> {
    gradleVersion = "8.1.1"
    distributionType = Wrapper.DistributionType.BIN
}
