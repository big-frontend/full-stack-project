import org.gradle.api.internal.classpath.ModuleRegistry
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.support.serviceOf
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java-gradle-plugin")
    alias(libs.plugins.kotlinJvm)
//    `kotlin-dsl`
}

val groupId: String by project
gradlePlugin {
    plugins {
        create("pisces-plugin") {
            id = "${groupId}.pisces-plugin"
            implementationClass = "com.electrolytej.pisces.PiscesPlugin"
            displayName = "pisces-plugin"
            description = "pisces-plugin"
        }
    }
}
group = "${groupId}.pisces-plugin"
description = "pisces-plugin"
version = "1.0.0"
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    testImplementation(libs.junit)
    compileOnly(gradleApi())
    compileOnly(libs.android.gradleplugin)
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
//plugins {
//    id "com.gradle.plugin-publish" version "0.15.0"
//}
////发布到远程gradle plugin portal，获得approval超级慢
//pluginBundle {
//    vcsUrl = 'https://github.com/JamesfChen/pisces'
//    website = "https://github.com/JamesfChen/pisces"
//    description = "pisces-plugin"
//    tags = ['pisces']
//    plugins {
//        rnbundlePlugin {
//            displayName = 'rnbundle-plugin'
//            tags = ['pisces']
//            version '1.0.0'
//        }
//        h5bundlePlugin {
//            displayName = 'h5bundle-plugin'
//            tags = ['pisces']
//            version '1.0.0'
//        }
//        flutterbundlePlugin {
//            displayName = 'flutterbundle-plugin'
//            tags = ['pisces']
//            version '1.0.0'
//        }
//    }
//    mavenCoordinates {
//        groupId = rootProject.groupId
//        artifactId = 'rnbundle-plugin'
//        version = "1.0.0"
//    }
//    mavenCoordinates {
//        groupId = rootProject.groupId
//        artifactId = 'h5bundle-plugin'
//        version = "1.0.0"
//    }
//    mavenCoordinates {
//        groupId = rootProject.groupId
//        artifactId = 'flutterbundle-plugin'
//        artifactId = 'api-plugin'
//        version = "1.0.0"
//    }
//}