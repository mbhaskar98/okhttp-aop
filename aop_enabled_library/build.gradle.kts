plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.ibotta.gradle.aop")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}


android {
    namespace = "com.example.aop_enabled_library"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("debug")
        }
    }

    /*
    Required when using above Kotlin 1.5, otherwise Kotlin lambdas will be compiled using
    LambdaMetafactory.metafactory() which means that aspects cannot be matched since the
    generated lambdas won't have the expected method names. You can see my write-up of the
    issue here:
    https://github.com/Ibotta/gradle-aspectj-pipeline-plugin/issues/8
     */
    kotlinOptions {
        freeCompilerArgs = listOf("-Xsam-conversions=class")
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

    // Okhttp for testing
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")


    // AspectJ - Required for the Ibotta AspectJ Plugin
    // That plugin is compiled against v1.9.6 but here we
    // are trying to use the latest to ensure compatibility
    compileOnly("org.aspectj:aspectjrt:1.9.22.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}