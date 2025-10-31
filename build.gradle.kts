import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.indra)
    alias(libs.plugins.indra.checkstyle)
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
}

indra {
    javaVersions {
        target(17)
        minimumToolchain(22)
    }
    checkstyle(libs.versions.checkstyle.get())
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name = project.name
                url = "https://github.com/Siebrenvde/ntfy-java"
                licenses {
                    license {
                        name = "The MIT License"
                        url = "https://opensource.org/licenses/MIT"
                    }
                }
                developers {
                    developer {
                        id = "siebrenvde"
                        name = "Siebrenvde"
                        email = "siebren@siebrenvde.dev"
                        url = "https://siebrenvde.dev"
                        timezone = "Europe/Brussels"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/Siebrenvde/ntfy-java.git"
                    developerConnection = "scm:git:ssh://git@github.com/Siebrenvde/ntfy-java.git"
                    url = "https://github.com/Siebrenvde/ntfy-java"
                }
            }
        }

        repositories.maven {
            val repo = if (version.toString().endsWith("-SNAPSHOT")) "snapshots" else "releases"
            url = uri("https://repo.siebrenvde.dev/${repo}/")
            name = "siebrenvde"
            credentials(PasswordCredentials::class)
        }
    }
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

tasks.compileJava {
    options.errorprone {
        check("NullAway", CheckSeverity.ERROR)
        option("NullAway:AnnotatedPackages", "dev.siebrenvde.ntfy")
        option("NullAway:JSpecifyMode", "true")
    }
}
