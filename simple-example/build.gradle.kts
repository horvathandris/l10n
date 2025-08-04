import io.github.horvathandris.localisation.generator.GenerateMessagesTask
import io.github.horvathandris.localisation.generator.Generator

repositories {
    mavenCentral()
}

plugins {
    java
    id("io.github.horvathandris.localisation")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<GenerateMessagesTask>("generateMessages") {
    messageBundleFile = file("src/main/resources/messages.properties")
    packageName = "io.github.horvathandris.example.l10n"
    type = Generator.Type.SIMPLE
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}