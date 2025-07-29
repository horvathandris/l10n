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
    implementation(project(":l10n-starter"))
    implementation("org.springframework.boot:spring-boot-starter:3.5.4")
    implementation("org.springframework:spring-context:6.2.9")

    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.5.4")
}

tasks.named<GenerateMessagesTask>("generateTranslationKeys") {
    messageBundleFile = file("src/main/resources/i18n/messages.properties")
    packageName = "dev.horvathandris.example.l10n"
    type = Generator.Type.SPRING
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}