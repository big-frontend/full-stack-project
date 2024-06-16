import org.gradle.api.internal.classpath.ModuleRegistry
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.support.serviceOf
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "1.2.1"
    alias(libs.plugins.kotlin.jvm)
//    `kotlin-dsl`
}
sourceSets {
    main {
        java {
            setSrcDirs(listOf("../pisces-annotation/src/main/kotlin"))
        }
    }
}

group = "io.github.electrolytej"
version = "1.0.0"
gradlePlugin {
    plugins {
        create("pisces-gradle-plugin") {
            id = "${group}.pisces-plugin"
            implementationClass = "com.electrolytej.pisces.PiscesPlugin"
            displayName = "pisces-gradle-plugin"
            description = "pisces-gradle-plugin"
        }
    }
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    testImplementation(libs.junit)
    compileOnly(gradleApi())
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    implementation(libs.javassist)
    implementation(libs.kotlin.stdlib)
    implementation(libs.electrolytej.baseplugin)
    implementation("com.squareup.okhttp3:okhttp:3.14.0")
    testRuntimeOnly(
        files(
            serviceOf<ModuleRegistry>()
                .getModule("gradle-tooling-api-builders")
                .classpath
                .asFiles
                .first()
        )
    )
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}
tasks.withType<Test>().configureEach {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}