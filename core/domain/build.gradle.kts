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
    api(libs.androidx.datastore.preferences)
//    implementation(libs.hilt.android)
//    ksp(libs.hilt.android.compiler)
    implementation("javax.inject:javax.inject:1")
}