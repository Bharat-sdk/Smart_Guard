import org.jetbrains.kotlin.fir.resolve.transformers.resolveToPackageOrClass

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id ("androidx.navigation.safeargs.kotlin")


}

kapt{
    correctErrorTypes = true
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
        multiDexEnabled = true
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
    kapt(Libs.ROOM_COMPILER)
    implementation(Libs.ROOM_KTX)
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
    // Koin Core features
    implementation (Libs.KOIN_CORE)
    implementation(Libs.KOIN_ANDROID)
    implementation(Libs.COROUTINE_LIB)
    //Firebase
    implementation( platform(Libs.FIREBASE_BOM))
    implementation(Libs.FIREBASE_CRASHLYTICS)
    //Logging
    implementation(Libs.TIMBER_LIB)
    //Memory Leak
   // debugImplementation(Libs.LEAK_CANARY_LIB)
    //Multidex
    implementation(Libs.MULTIDEX_LIB)
    // Dp SP Support
    implementation(Libs.DP_LIB)
    implementation(Libs.SP_LIB)

    // Navigation Components
    implementation (Libs.NAV_FRAGMENT_LIB)
    implementation (Libs.NAV_UI_LIB)
    implementation (Libs.VIEW_PAGER_DOTS)

    //Image Picker Library
    implementation ("com.github.dhaval2404:imagepicker:2.1")





}