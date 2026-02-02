plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
//    alias(libs.plugins.ksp)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
//    implementation(libs.androidx.hilt.navigation.compose)
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.auth)
//    implementation(libs.okhttp)
//    implementation(libs.okhttp.logging.interceptor)
//    implementation(libs.retrofit)
//    implementation(libs.retrofit2.kotlinx.serialization.converter)
//    implementation(libs.androidx.datastore.preferences)
//    implementation(libs.hilt.android)
//    implementation(libs.androidx.credentials)
//    ksp(libs.hilt.android.compiler)
//    implementation(libs.kotlinx.serialization.json)

    implementation("javax.inject:javax.inject:1")
    implementation(projects.core.domain)
}