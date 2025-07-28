import dev.horvathandris.localisation.generator.GenerateMessagesTask
import dev.horvathandris.localisation.generator.Generator

repositories {
    mavenCentral()
}

plugins {
    java
    id("dev.horvathandris.localisation")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<GenerateMessagesTask>("generateTranslationKeys") {
    messageBundlePath = "src/main/resources/messages.properties"
    packageName = "dev.horvathandris.example.l10n"
    type = Generator.Type.SIMPLE
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}