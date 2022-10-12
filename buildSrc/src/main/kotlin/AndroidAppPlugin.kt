import org.gradle.api.Project
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidAppPlugin : BasePlugin() {
    override fun configuredAndroidBlock() {
        project.extensions.getByType<BaseExtension>().run {
            buildToolsVersion(AndroidSdk.buildVersion)
            compileSdkVersion(AndroidSdk.TARGET_SDK)

            defaultConfig {
                minSdk = AndroidSdk.MIN_Sdk
                targetSdk = AndroidSdk.TARGET_SDK
                versionCode = AndroidSdk.VERSION_CODE
                versionName = AndroidSdk.VERSION_NAME

                testInstrumentationRunner =
                    "androidx.test.runner.AndroidJUnitRunner"
                vectorDrawables {
                    useSupportLibrary = true
                }
                multiDexEnabled = true
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_1
                targetCompatibility = JavaVersion.VERSION_1_1
            }
            project.tasks.withType(KotlinCompile::class.java).all {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_1_1.toString()

                }

            }
            testOptions {
                unitTests.apply {
                    isReturnDefaultValues = true
                }
                animationsDisabled = true
            }
            buildTypes {
                getByName(BuildType.DEBUG) {
            isMinifyEnabled=BuildTypeDebug.isMinifyEnabled

                }
                getByName(BuildType.RELEASE) {
                    isMinifyEnabled=BuildTypeRelease.isMinifyEnabled
                    isDebuggable=false
                    isTestCoverageEnabled=BuildTypeRelease.isTestCoverageEnabled

                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )

                }

            }


        }


    }
}