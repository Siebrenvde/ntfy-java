import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.indra)
    alias(libs.plugins.indra.checkstyle)
    alias(libs.plugins.indra.publishing)
    alias(libs.plugins.blossom)
    alias(libs.plugins.errorprone)
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnlyApi(libs.jspecify)
    compileOnlyApi(libs.jetbrains.annotations)
    implementation(libs.gson)

    errorprone(libs.errorprone)
    errorprone(libs.nullaway)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform)
}

indra {
    javaVersions {
        target(17)
        minimumToolchain(22)
        testWith(17, 21, 25)
    }
    checkstyle(libs.versions.checkstyle.get())

    mitLicense()
    github("Siebrenvde", "ntfy-java")

    publishReleasesTo("siebrenvde-releases", "https://repo.siebrenvde.dev/releases/")
    publishSnapshotsTo("siebrenvde-snapshots", "https://repo.siebrenvde.dev/snapshots/")

    configurePublications {
        pom {
            developers {
                developer {
                    id = "siebrenvde"
                    name = "Siebrenvde"
                    email = "siebren@siebrenvde.dev"
                    url = "https://siebrenvde.dev"
                    timezone = "Europe/Brussels"
                }
            }
        }
    }
}

tasks.requireTagged { enabled = false }
tasks.signMavenPublication { enabled = false }

tasks.javadoc.configure {
    val options = options as StandardJavadocDocletOptions
    options.links(
        "https://jspecify.dev/docs/api/",
        "https://javadoc.io/doc/org.jetbrains/annotations/${libs.jetbrains.annotations.get().version}/"
    )
}

sourceSets.main {
    blossom.javaSources {
        property("version", project.version.toString())
    }
}

tasks.withType<JavaCompile> {
    options.errorprone {
        check("NullAway", CheckSeverity.ERROR)
        option("NullAway:AnnotatedPackages", "dev.siebrenvde.ntfy")
        option("NullAway:JSpecifyMode", "true")
    }
}
