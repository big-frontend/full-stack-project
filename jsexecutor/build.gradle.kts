plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = 32
//    ndkVersion = "22.0.7026061"
    ndkVersion = "25.0.8775105"
    defaultConfig {
        minSdk = 21
        targetSdk = 32
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                arguments += listOf(
                    "-DANDROID_ARM_NEON=TRUE",
                    "-DANDROID_TOOLCHAIN=clang",
                    "-DANDROID_STL=c++_shared"
                )
                cppFlags += listOf(
                    "-s"/*去除so lib 的符号表（libguard shrink之后的size2139k -->283k 7倍）*/,
                    "-fvisibility=hidden",
                    "-ffunction-sections",
                    "-fdata-sections",
                    "-std=c++14"
                )
                cFlags += listOf("-D__STDC_FORMAT_MACROS")
//                cppFlags "-std=gnu++11"
//                targets "hawks","hotfix"
                abiFilters += listOf(
                    "armeabi-v7a",
//                    "arm64-v8a",
                    /*,"x86","x86_64"*/   //输出制定三种abi体系结构下的so库
                )
            }
        }
    }
    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
        }
    }
    buildFeatures {
        prefab = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    // https://mvnrepository.com/artifact/com.eclipsesource.j2v8/j2v8
    api("com.eclipsesource.j2v8:j2v8:6.2.1@aar")
//    implementation("org.webkit:android-jsc:r174650")
//    api("io.github.taoweiji.quickjs:quickjs-android:1.+")
    api("com.facebook.fbjni:fbjni:0.2.2")
    implementation("com.facebook.soloader:soloader:0.10.4+")
}