plugins {
    alias(libs.plugins.kotlin.jvm)
}

version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":l10n"))

    implementation(libs.spring.boot.autoconfigure)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}