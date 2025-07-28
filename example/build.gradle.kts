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
    messageBundlePath = "src/test/resources/messages.properties"
    packageName = "dev.horvathandris.example"
    language = Generator.Language.KOTLIN
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}