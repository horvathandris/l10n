import dev.horvathandris.localisation.generator.GenerateTranslationKeysTask

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

tasks.named<GenerateTranslationKeysTask>("generateTranslationKeys") {
    messageBundlePath.set("src/test/resources/messages.properties")
    packageName.set("dev.horvathandris.example")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}