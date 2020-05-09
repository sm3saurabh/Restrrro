
plugins {
    id("com.android.application")
    id("kotlinx-serialization")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Application.COMPILE_SDK_VERSION)
    buildToolsVersion = Versions.BUILD_TOOLS_VERSION
    defaultConfig {
        applicationId = "com.restaurantfinder"
        minSdkVersion(Application.MIN_SDK_VERSION)
        targetSdkVersion(Application.TARGET_SDK_VERSION)
        versionCode = Application.VERSION_CODE
        versionName = Application.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    // For Kotlin projects
    kotlinOptions {
        jvmTarget = "1.8"
    }

    dataBinding {
        isEnabled = true
    }

}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":network"))
    implementation(project(":database"))


    // Individual dependencies
    implementation(Dependencies.kotlin)
    implementation(Dependencies.coroutines)
    implementation(Dependencies.koin)
    implementation(Dependencies.transition)
    implementation(Dependencies.recyclerView)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.picasso)
    implementation(Dependencies.location)
    implementation(Dependencies.retrofit)


    // Groups of dependencies
    appCompat()
    epoxy()
    defaultTests()
    lifecycle()
    room()


}
