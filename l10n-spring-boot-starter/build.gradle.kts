plugins {
    id("kotlin-common-conventions")
    id("publish-conventions")
}

dependencies {
    api(project(":l10n-spring"))

    implementation(libs.spring.boot.autoconfigure)
}

publishing {
    publications {
        named<MavenPublication>("mavenJava") {
            pom {
                name = "L10n Spring Boot Starter"
                description = "Spring Boot starter for the L10n Spring helpers."
            }
        }
    }
}