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
    implementation(project(":l10n-spring-boot-starter"))

    implementation(libs.spring.boot.starter)
    implementation(libs.spring.context)

    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation(libs.spring.boot.starter.test)
}

tasks.named<GenerateMessagesTask>("generateMessages") {
    messageBundleFile = file("src/main/resources/i18n/messages.properties")
    packageName = "dev.horvathandris.example.l10n"
    type = Generator.Type.SPRING
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}