import io.github.horvathandris.localisation.generator.GenerateL10nMessagesTask
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

tasks.named<GenerateL10nMessagesTask>("generateL10nMessages") {
    messageBundleFile = file("src/main/resources/messages.properties")
    generatorConfig<SimpleJavaConfiguration> {
        packageName = "io.github.horvathandris.example.l10n"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}