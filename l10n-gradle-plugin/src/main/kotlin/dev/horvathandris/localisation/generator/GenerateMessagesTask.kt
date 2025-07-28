package dev.horvathandris.localisation.generator

import dev.horvathandris.localisation.getGeneratedSourceSetPath
import dev.horvathandris.localisation.parser.MessageParser
import org.gradle.api.DefaultTask
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class GenerateMessagesTask : DefaultTask() {

    init {
        description = "Task to generate code for translation keys defined in a message bundle"
        group = BasePlugin.BUILD_GROUP
    }

    @get:Input
    abstract val messageBundleFile: Property<File>

    @get:Input
    abstract val packageName: Property<String>

    @get:Input
    abstract val language: Property<Generator.Language>

    @get:Input
    abstract val type: Property<Generator.Type>

    @TaskAction
    fun action() {
        cleanOutputDirectory()

        val messages = MessageParser(messageBundleFile.get()).parse()

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
        val generatedDir = project.layout.buildDirectory.dir("generated/sources/l10n").get().asFile
        if (generatedDir.exists()) {
            generatedDir.deleteRecursively()
        }
    }

    private fun writeOutputFile(outputFileName: String, outputFileContent: String) {
        val packageDirectory = packageName.get().replace(".", "/")
        val outputFile = project.layout.buildDirectory
            .file("${project.getGeneratedSourceSetPath()}/$packageDirectory/$outputFileName")
            .get().asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText(outputFileContent)
    }

}