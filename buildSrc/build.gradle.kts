plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
    maven(url = uri(  "https://jitpack.io"))

}
dependencies{
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    implementation("com.android.tools.build:gradle:7.2.1")
    implementation(gradleApi())
    implementation(localGroovy())
}