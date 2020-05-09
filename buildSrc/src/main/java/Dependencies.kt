import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"

    const val junit = "junit:junit:${Versions.JUNIT_VERSION}"
    const val testRunner = "androidx.test:runner:${Versions.TEST_RUNNER_VERSION}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_VERSION}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.ROOM_VERSION}"
    const val roomAnnotationProcessor = "androidx.room:room-compiler:${Versions.ROOM_VERSION}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.ROOM_VERSION}"

    const val kotlinSerialRuntime =
        "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.KOTLIN_SERIALIZATION_VERSION}"
    const val kotlinSerialConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.KOTLIN_SERIALIZATION_CONVERTER_VERSION}"

    const val lifecycleLiveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_VERSION}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VERSION}"

    const val epoxyCore = "com.airbnb.android:epoxy:${Versions.EPOXY_VERSION}"
    const val epoxyDataBinding = "com.airbnb.android:epoxy-databinding:${Versions.EPOXY_VERSION}"
    const val epoxyProcessor = "com.airbnb.android:epoxy-processor:${Versions.EPOXY_VERSION}"

    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE_VERSION}"

    const val koin = "org.koin:koin-android-viewmodel:${Versions.KOIN_VERSION}"

    const val transition = "androidx.transition:transition:${Versions.TRANSITION_VERSION}"

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.RECYCLER_VIEW_VERSION}"

    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT_VERSION}"

    const val location =
        "com.google.android.gms:play-services-location:${Versions.LOCATION_PROVIDER_VERSION}"

    const val picasso = "com.squareup.picasso:picasso:${Versions.PICASSO_VERSION}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.APP_COMPAT_VERSION}"
    const val androidXLegacySupport = "androidx.legacy:legacy-support-v4:${Versions.ANDROIDX_LEGACY_SUPPORT_VERSION}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.CORE_KTX_VERSION}"

    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.GRADLE_PLUGIN_VERSION}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}"
    const val kotlinSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN_VERSION}"

}

fun DependencyHandler.appCompat() {
    implementation(Dependencies.appCompat)
    implementation(Dependencies.androidXLegacySupport)
    implementation(Dependencies.coreKtx)
}

fun DependencyHandler.epoxy() {
    implementation(Dependencies.epoxyCore)
    implementation(Dependencies.epoxyDataBinding)
    kapt(Dependencies.epoxyProcessor)
}

fun DependencyHandler.room() {
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    kapt(Dependencies.roomAnnotationProcessor)
}

fun DependencyHandler.lifecycle() {
    implementation(Dependencies.lifecycleLiveData)
    implementation(Dependencies.lifecycleViewModel)
}

fun DependencyHandler.kotlinSerialization() {
    implementation(Dependencies.kotlinSerialRuntime)
    implementation(Dependencies.kotlinSerialConverter)
}

fun DependencyHandler.defaultTests() {
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.testRunner)
    androidTestImplementation(Dependencies.espresso)
}

private fun DependencyHandler.implementation(dep: String) {
    add("implementation", dep)
}

private fun DependencyHandler.kapt(dep: String) {
    add("kapt", dep)
}

private fun DependencyHandler.androidTestImplementation(dep: String) {
    add("androidTestImplementation", dep)
}

private fun DependencyHandler.testImplementation(dep: String) {
    add("testImplementation", dep)
}