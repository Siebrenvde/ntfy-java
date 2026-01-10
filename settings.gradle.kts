pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.siebrenvde.dev/releases/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "ntfy-java"
