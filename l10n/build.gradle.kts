plugins {
    kotlin("jvm") version "2.2.0"
}

group = "dev.horvathandris.localisation"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:6.2.9")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}