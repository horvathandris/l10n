package io.github.horvathandris.localisation

import io.github.horvathandris.localisation.generator.GenerateMessagesTask
import io.github.horvathandris.localisation.generator.Generator
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

private const val GENERATE_MESSAGES_TASK_NAME = "generateMessages"
private const val COMPILE_JAVA_TASK_NAME = "compileJava"
private const val SOURCE_SETS = "sourceSets"
private const val MAIN_SOURCE_SET = "main"

class L10nPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        registerGenerateMessagesTask(project)
    }

    private fun registerGenerateMessagesTask(project: Project) {
        project.tasks.register(
            GENERATE_MESSAGES_TASK_NAME,
            GenerateMessagesTask::class.java,
        ) {
            it.type.convention(Generator.Type.SIMPLE)
            it.language.convention(Generator.Language.JAVA)
        }

        project.pluginManager.withPlugin("java") {
            project.tasks.named(COMPILE_JAVA_TASK_NAME) {
                it.dependsOn(GENERATE_MESSAGES_TASK_NAME)
            }

            // Add generated output directory to the main source-set during configuration
            (project.properties[SOURCE_SETS] as SourceSetContainer)
                .getByName(MAIN_SOURCE_SET)
                .java
                .srcDir(project.getGeneratedSourceSetPath())
        }
    }
}

fun Project.getGeneratedSourceSetPath(): String {
    val buildDirPath = layout.buildDirectory.get().asFile.toURI().toURL().path.removeSuffix("/")
    return "$buildDirPath/generated/sources/l10n/$MAIN_SOURCE_SET/java"
}