import io.github.horvathandris.localisation.generator.GenerateMessagesTask
import io.github.horvathandris.localisation.generator.configuration.GeneratorConfiguration.SimpleJavaConfiguration

plugins {
    java
    id("io.github.horvathandris.localisation")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<GenerateMessagesTask>("generateMessages") {
    messageBundleFile = file("src/main/resources/messages.properties")
    configuration<SimpleJavaConfiguration> {
        packageName = "io.github.horvathandris.example.l10n"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}