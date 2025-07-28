package dev.horvathandris.localisation.generator

import dev.horvathandris.localisation.parser.MessageParser
import org.gradle.api.DefaultTask
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

private const val GENERATED_OUTPUT_DIRECTORY = "generated/sources/l10n"

abstract class GenerateMessagesTask : DefaultTask() {

    init {
        description = "Task to generate code for translation keys defined in a message bundle"
        group = BasePlugin.BUILD_GROUP
    }

    @get:Input
    abstract val messageBundlePath: Property<String>

    @get:Input
    abstract val packageName: Property<String>

    @get:Input
    abstract val language: Property<Generator.Language>

    @get:Input
    abstract val type: Property<Generator.Type>

    @TaskAction
    fun action() {
        cleanOutputDirectory()

        val messages = MessageParser(project.file(messageBundlePath.get())).parse()

        val generator = GeneratorFactory.get(
            type.get(),
            language.get(),
            packageName.get(),
            indentSize = 4,
        )
        val (outputFileName, outputFileContent) = generator.generate(messages)
        writeOutputFile(outputFileName, outputFileContent)
    }

    private fun cleanOutputDirectory() {
        val generatedDir = project.layout.buildDirectory.dir(GENERATED_OUTPUT_DIRECTORY).get().asFile
        if (generatedDir.exists()) {
            generatedDir.deleteRecursively()
        }
    }

    private fun writeOutputFile(outputFileName: String, outputFileContent: String) {
        val packageDirectory = packageName.get().replace(".", "/")
        val outputFile = project.layout.buildDirectory
            .file("$GENERATED_OUTPUT_DIRECTORY/java/main/$packageDirectory/$outputFileName")
            .get().asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText(outputFileContent)
    }

}