plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    compileSdk =Versions.COMPILE_SDK

    defaultConfig {
        applicationId ="com.hbeonlabs.smartguard"
        minSdk=Versions.MIN_Sdk
        targetSdk=Versions.TARGET_SDK
        versionCode =Versions.VERSION_CODE
        versionName =Versions.VERSION_NAME

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
    buildFeatures {
        dataBinding= true
    }
}

dependencies {

    implementation(Libs.KTS_CORE_LIB)
    implementation(Libs.APP_COMPAT_LIB)
    implementation (Libs.MATERIAL_LIB)
    implementation (Libs.CONSTRAINT_LAYOUT_LIB)
    implementation(Libs.ROOM_RUN_TIME)
    annotationProcessor(Libs.ROOM_COMPILER)
    testImplementation(Libs.JUNIT_LIB)
    androidTestImplementation(Libs.JUNIT_EXT_LIB)
    androidTestImplementation(Libs.ESPRESSO_CORE)
    // ViewModel
    implementation(Libs.VIEW_MODEL)

    // LiveData
    implementation(Libs.LIVE_DATA)
    // Annotation processor
    kapt(Libs.LIFECYCLE_COMPILER)
    // alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation(Libs.LIFECYCELE_JAVA_SUPPORT)
    //Koin -- Dependency  Injection
    implementation(Libs.KOIN_CORE)
    implementation( platform(Libs.FIREBASE_BOM))

    // Koin main features for Android
    implementation (Libs.KOIN_ANDROID)

// Java Compatibility
    implementation (Libs.KOIN_COMPAT)
// Jetpack WorkManager
    implementation(Libs.KOIN_WORKMANAGER)
// Navigation Graph
    implementation(Libs.KOIN_NAVIGATION)
    implementation(Libs.TIMBER_LIB)
    debugImplementation(Libs.LEAK_CANARY_LIB)
}