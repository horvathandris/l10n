plugins {
    `java-library`
    `maven-publish`
    signing
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            artifact(tasks.named("sourcesJar"))
            artifact(tasks.named("javadocJar"))

            pom {
                url = "https://github.com/horvathandris/l10n"
                licenses {
                    license {
                        name = "Apache License 2.0"
                        url = "https://github.com/horvathandris/l10n/blob/main/LICENSE"
                    }
                }
                developers {
                    developer {
                        id = "horvathandris"
                        name = "Andris Horváth"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/horvathandris/l10n.git"
                    developerConnection = "scm:git:ssh://github.com/horvathandris/l10n.git"
                    url = "https://github.com/horvathandris/l10n"
                }
            }
        }
    }
    repositories {
        maven {
            url = layout.buildDirectory.dir("staging-deploy").get().asFile.toURI()
        }
    }
}