import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose)
}

kotlin {
//    val hostOs = System.getProperty("os.name")
//    val isArm64 = System.getProperty("os.arch") == "aarch64"
//    val isMingwX64 = hostOs.startsWith("Windows")
//    val nativeTarget = when {
//        hostOs == "Mac OS X" && isArm64 -> macosArm64("native")
//        hostOs == "Mac OS X" && !isArm64 -> macosX64("native")
//        hostOs == "Linux" && isArm64 -> linuxArm64("native")
//        hostOs == "Linux" && !isArm64 -> linuxX64("native")
//        isMingwX64 -> mingwX64("native")
//        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
//    }
//
//    nativeTarget.apply {
//        compilations.getByName("main") {
//            cinterops {
//                val libcurl by creating {
//                    defFile(project.file("src/nativeInterop/cinterop/libcurl.def"))
//                    packageName("com.spacecraft.kmp")
//                    compilerOpts("-I/path")
//                    includeDirs.allHeaders("path")
//                }
//            }
//        }
//        binaries {
//            executable {
//                entryPoint = "main"
//            }
//        }
//    }
    //指定main函数入口
//    fun KotlinNativeTarget.config() {
//        binaries {
//            executable {
//                entryPoint = "main"
//            }
//        }
//    }
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
//    linuxX64() {
//        val main by compilations.getting {
//            cinterops {
//                val libcurl by creating {
//                    defFile(project.file("src/nativeInterop/cinterop/libcurl.def"))
////                    packageName("com.spacecraft.kmp")
//                    compilerOpts("-I/src/nativeInterop/cinterop/")
//                    includeDirs.allHeaders("src/nativeInterop/cinterop/")
//                }
//            }
//        }
//        binaries {
//            executable {
//                entryPoint = "main"
//            }
//        }
//    }
//    mingwX64() // on Windows
//    macosX64() { // on macOS
//        binaries {
//            executable()
//        }
//    }
//    androidNativeArm64()
//    androidNativeArm32 {
//        binaries {
//            sharedLib("aa", listOf(RELEASE))
//        }
//        compilations.getByName("main") {
//            cinterops {
//                val libcurl by creating {
//                    defFile(project.file("src/nativeInterop/cinterop/libcurl.def"))
//                    packageName("com.spacecraft.kmp")
//                    compilerOpts("-I/path")
//                    includeDirs.allHeaders("path")
//                }
//            }
//        }
//    }
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            api(compose.ui)
            api(compose.components.resources)
        }
        val desktopMain by getting {
            dependencies {

            }
        }
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            api("androidx.databinding:viewbinding:8.1.4")
        }
    }
}

android {
    namespace = "com.electrolytej.pisces"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
tasks.withType<Wrapper> {
    gradleVersion = "8.1.1"
    distributionType = Wrapper.DistributionType.BIN
}
