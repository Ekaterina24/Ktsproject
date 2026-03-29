import dev.detekt.gradle.Detekt

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    id("dev.detekt") version("2.0.0-alpha.2")
    id("com.google.gms.google-services") version "4.4.4" apply false
    id("com.google.firebase.crashlytics") version "3.0.6" apply false
}

repositories {
    mavenCentral()
}

detekt {
    buildUponDefaultConfig = true
    autoCorrect = true
    config.setFrom(files("$rootDir/config.yml"))
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}

tasks.withType<Detekt>().configureEach {
    setSource(files(rootDir))
    include("**/*.kt")
    exclude("**/build/**")

    reports {
        html.required.set(true)
        sarif.required.set(true)
    }
}
