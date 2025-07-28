package dev.horvathandris.localisation.generator

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class GenerateTranslationKeysTask : DefaultTask() {

    init {
        description = "Task to generate code for translation keys defined in a message bundle"
        group = BasePlugin.BUILD_GROUP
    }

    @get:Input
    abstract val messageBundlePath: Property<String>

    @get:Input
    abstract val packageName: Property<String>

    @get:Input
    abstract val language: Property<GeneratorLanguage>

    @get:Input
    abstract val type: Property<GeneratorType>

    @TaskAction
    fun action() {
        val messages = parseMessages()

        val generator = GeneratorFactory.get(
            type.get(),
            language.get(),
            packageName.get(),
            indentSize = 4,
        )
        val (outputFileName, outputFileContent) = generator.generate(messages)
        writeOutputFile(outputFileName, outputFileContent)
    }

    private fun parseMessages() = project.file(messageBundlePath.get())
        .readLines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .filter { !it.startsWith("#") && !it.startsWith("!") }
        .map { it.split("=") }
        .map { (key, value) -> key.trimEnd() to value.trimStart() }
        .fold(mutableMapOf<String, MessageComponent>()) { messages, (key, value) ->
            val keyComponents = key.split(".")
            var currentNode = messages
            for (component in keyComponents.dropLast(1)) {
                currentNode = currentNode.computeIfAbsent(component) {
                    MessageComponent()
                }.children
            }
            currentNode[keyComponents.last()] = MessageComponent(key, value)
            messages
        }


    private fun writeOutputFile(outputFileName: String, outputFileContent: String) {
        val packageDirectory = packageName.get().replace(".", "/")
        val outputFile = project.layout.buildDirectory
            .file("generated/sources/l10n/java/main/$packageDirectory/$outputFileName")
            .get().asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText(outputFileContent)
    }

}