// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven ( "https://plugins.gradle.org/m2/")
    }
    dependencies {
        //Android Gradle Plugin (AGP)
        classpath("com.android.tools.build:gradle:8.3.0")

        //Kotlin
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20")

        //AOP
        classpath("com.ibotta:plugin:1.4.1")
    }
}