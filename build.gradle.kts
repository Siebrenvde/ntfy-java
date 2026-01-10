import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
    id("java-library")
    alias(libs.plugins.indra)
    alias(libs.plugins.indra.checkstyle)
    alias(libs.plugins.blossom)
    alias(libs.plugins.errorprone)
    alias(libs.plugins.publisher)
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
}

publisher {
    github("Siebrenvde", "ntfy-java")
}

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
