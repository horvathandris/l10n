package dev.horvathandris.localisation

import dev.horvathandris.localisation.generator.GenerateMessagesTask
import dev.horvathandris.localisation.generator.Generator
import org.gradle.api.Plugin
import org.gradle.api.Project

private const val EXTENSION_NAME = "l10nPluginExtension"
private const val GENERATE_TRANSLATION_KEYS_TASK_NAME = "generateTranslationKeys"
private const val COMPILE_JAVA_TASK_NAME = "compileJava"

class L10nPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create(
            EXTENSION_NAME,
            L10nPluginExtension::class.java,
        )
        registerGenerateTranslationKeysTask(project, extension)
    }

    private fun registerGenerateTranslationKeysTask(project: Project, extension: L10nPluginExtension) {
        project.tasks.register(
            GENERATE_TRANSLATION_KEYS_TASK_NAME,
            GenerateMessagesTask::class.java,
        ) {
            it.messageBundlePath.set(extension.messageBundlePath)
            it.type.convention(Generator.Type.SIMPLE)
            it.language.convention(Generator.Language.JAVA)
        }

        project.tasks.getByName(COMPILE_JAVA_TASK_NAME)
            .dependsOn(GENERATE_TRANSLATION_KEYS_TASK_NAME)
    }
}