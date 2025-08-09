import io.github.horvathandris.localisation.generator.GenerateMessagesTask
import io.github.horvathandris.localisation.generator.configuration.GeneratorConfiguration.SpringJavaConfiguration

repositories {
    mavenCentral()
}

plugins {
    java
    id("io.github.horvathandris.localisation")
}

dependencies {
    implementation(project(":l10n-spring-boot-starter"))

    implementation(libs.spring.boot.starter)
    implementation(libs.spring.context)

    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation(libs.spring.boot.starter.test)
}

tasks.named<GenerateMessagesTask>("generateMessages") {
    messageBundleFile = file("src/main/resources/i18n/messages.properties")
    configuration<SpringJavaConfiguration> {
        packageName = "io.github.horvathandris.example.l10n"
        indentSize = 2
        useFormatAsArgumentName = true
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}