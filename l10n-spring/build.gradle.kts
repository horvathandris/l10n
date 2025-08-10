plugins {
    id("kotlin-common-conventions")
    id("publish-conventions")
}

dependencies {
    implementation(libs.spring.context)
}

publishing {
    publications {
        named<MavenPublication>("mavenJava") {
            pom {
                name = "L10n Spring"
                description = "Spring context helpers for the Localisation CodeGen Plugin."
            }
        }
    }
}