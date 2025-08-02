plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.indra)
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnlyApi(libs.jspecify)
    compileOnlyApi(libs.jetbrains.annotations)
}

indra {
    javaVersions {
        target(17)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }

        repositories.maven {
            val repo = if (version.toString().endsWith("-SNAPSHOT")) "snapshots" else "releases"
            url = uri("https://repo.siebrenvde.dev/${repo}/")
            name = "siebrenvde"
            credentials(PasswordCredentials::class)
        }
    }
}
