plugins {
    alias(libs.plugins.kotlin.jvm)
}

version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.context)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}