import dev.horvathandris.localisation.generator.GenerateMessagesTask

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
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}