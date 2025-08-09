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
        withType<MavenPublication> {
            getByName("publish") {
                artifact(tasks.named("sourcesJar"))
                artifact(tasks.named("javadocJar"))
            }
        }
    }
}