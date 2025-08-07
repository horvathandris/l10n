plugins {
    `java-gradle-plugin`
    `maven-publish`
    signing
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.jreleaser)
}

dependencyLocking {
    lockAllConfigurations()
    lockMode = LockMode.STRICT
}

repositories {
    mavenCentral()
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin)
        }

        val functionalTest by registering(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin)

            dependencies {
                implementation(project())
            }

            targets {
                all {
                    testTask.configure { shouldRunAfter(test) }
                }
            }
        }
    }
}

gradlePlugin.testSourceSets.add(sourceSets["functionalTest"])

tasks.named<Task>("check") {
    dependsOn(testing.suites.named("functionalTest"))
}

gradlePlugin {
    val l10n by plugins.creating {
        id = "io.github.horvathandris.localisation"
        implementationClass = "io.github.horvathandris.localisation.L10nPlugin"
        displayName = "Localisation CodeGen Plugin"
        description = "Plugin for generating aergonomic code from translation message keys."
        website = "https://github.com/horvathandris/l10n"
        vcsUrl = "https://github.com/horvathandris/l10n"
        tags = listOf(
            "i18n", "internationalization", "internationalisation",
            "l10n", "localization", "localisation",
            "codegen", "code-generation"
        )
    }
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}

fun MavenPublication.defaultPom() = pom {
    name = "Localisation CodeGen Plugin"
    description = "Plugin for generating aergonomic code from translation message keys."
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
        url = "http://github.com/horvathandris/l10n"
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            getByName("pluginMaven") {
                artifact(tasks.named("sourcesJar"))
                artifact(tasks.named("javadocJar"))
            }
            all {
                defaultPom()
            }
        }
    }
    repositories {
        maven {
            url = layout.buildDirectory.dir("staging-deploy").get().asFile.toURI()
        }
    }
}

jreleaser {
    signing {
        setActive("ALWAYS")
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    setActive("ALWAYS")
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository("build/staging-deploy")
                    gitRootSearch = true
                }
            }
        }
    }
}

val signTasks: List<Sign> = signing.sign(publishing.publications)
tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(signTasks)
}