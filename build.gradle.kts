// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = uri(  "https://jitpack.io"))

    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.google.gms:google-services:4.3.13")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.1")

    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}