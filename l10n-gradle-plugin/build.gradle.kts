plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    // Use Maven Central for resolving dependencies.
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

gradlePlugin {
    val l10n by plugins.creating {
        id = "dev.horvathandris.localisation"
        implementationClass = "dev.horvathandris.localisation.L10nPlugin"
        displayName = "Localisation CodeGen"
        description = "Plugin for generating aergonomic code from translation message keys."
        tags = listOf(
            "i18n", "internationalization", "internationalisation",
            "l10n", "localization", "localisation",
            "codegen", "code-generation"
        )
    }
}

gradlePlugin.testSourceSets.add(sourceSets["functionalTest"])

tasks.named<Task>("check") {
    dependsOn(testing.suites.named("functionalTest"))
}
