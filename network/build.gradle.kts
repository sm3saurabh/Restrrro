plugins {
    id("com.android.library")
    id("kotlinx-serialization")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}


android {
    compileSdkVersion(Application.COMPILE_SDK_VERSION)
    buildToolsVersion = Versions.BUILD_TOOLS_VERSION


    defaultConfig {
        minSdkVersion(Application.MIN_SDK_VERSION)
        targetSdkVersion(Application.TARGET_SDK_VERSION)
        versionCode = Application.VERSION_CODE
        versionName = Application.VERSION_NAME

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.coroutines)


    appCompat()
    defaultTests()
    kotlinSerialization()

}
