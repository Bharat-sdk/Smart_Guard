import org.gradle.api.Project
import com.android.build.gradle.BaseExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.initPlugins()
{
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-parcelize")
    plugins.apply("kotlin-kapt")
    plugins.apply("org.jetbrains.kotlin.android")
}

internal fun Project.configureJetPackNavigation()
{
    extensions.getByType<BaseExtension>().run{
       dependencies{
           add("implementation",Libs.NAV_FRAGMENT_LIB)
           add("implementation",Libs.NAV_UI_LIB)

       }
    }
}

internal fun Project.configureCommonDependencies()
{
    extensions.getByType<BaseExtension>().run{
        dependencies{
            add("implementation",Libs.APP_COMPAT_LIB)
            add("implementation",Libs.CONSTRAINT_LAYOUT_LIB)
            add("implementation",Libs.KTS_CORE_LIB)
            add("implementation",Libs.KOIN_ANDROID)
            add("implementation",Libs.KOIN_CORE)
            add("implementation",Libs.VIEW_MODEL)
            add("implementation",Libs.ROOM_COMPILER)
            add("implementation",Libs.ROOM_KTX)
            add("implementation",Libs.ROOM_RUN_TIME)
            add("implementation",Libs.ROOM_COROUTINE)
            add("implementation",Libs.LIVE_DATA)
            add("implementation",Libs.LIFECYCLE_COMPILER)
            add("implementation",Libs.TIMBER_LIB)
            add("implementation",Libs.MULTIDEX_LIB)
            add("implementation",Libs.DP_LIB)
            add("implementation",Libs.SP_LIB)
            add("implementation",Libs.IMAGE_PICKER_LIBRARY)
            add("implementation",Libs.FIREBASE_BOM)
            add("implementation",Libs.FIREBASE_CRASHLYTICS)
        }
    }
}

internal fun Project.tesingDepencies()
{
    extensions.getByType<BaseExtension>().run{
        dependencies{
            add("implementation",Libs.JUNIT_LIB)
            add("implementation",Libs.JUNIT_EXT_LIB)
            add("implementation",Libs.ESPRESSO_CORE)
            add("implementation",Libs.KOIN_ANDROID)
        }
    }
}