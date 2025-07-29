plugins {
    kotlin("jvm") version "2.2.0"
}

group = "dev.horvathandris.localisation"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":l10n"))

    implementation("org.springframework.boot:spring-boot-autoconfigure:3.5.4")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}