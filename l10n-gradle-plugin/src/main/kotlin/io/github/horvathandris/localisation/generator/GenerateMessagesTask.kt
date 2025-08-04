package io.github.horvathandris.localisation.generator

import io.github.horvathandris.localisation.parser.MessageParser
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class GenerateMessagesTask : DefaultTask() {

    @get:Input
    abstract val messageBundleFile: Property<File>

    @get:Input
    abstract val packageName: Property<String>

    @get:Input
    abstract val language: Property<Generator.Language>

    @get:Input
    abstract val type: Property<Generator.Type>

    private val outputDirectory: Directory

    init {
        description = "Task to generate code for translation keys defined in a message bundle"
        group = BasePlugin.BUILD_GROUP
        outputDirectory = project.layout.buildDirectory.dir("generated/sources/l10n").get()
    }

    @TaskAction
    fun action() {
        cleanOutputDirectory()

        val messages = MessageParser().parse(messageBundleFile.get())

        val generator = GeneratorFactory.get(
            type.get(),
            language.get(),
            packageName.get(),
            indentSize = 4,
        )
        generator.generate(messages).forEach { writeOutputFile(it) }
    }

    private fun cleanOutputDirectory() {
        val generatedDir = outputDirectory.asFile
        if (generatedDir.exists()) {
            generatedDir.deleteRecursively()
        }
    }

    private fun writeOutputFile(generatorOutput: Generator.Output) {
        val packageDirectory = packageName.get().replace(".", "/")
        val outputFile = outputDirectory
            .file("main/java/$packageDirectory/${generatorOutput.filename}")
            .asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText(generatorOutput.content)
    }

}