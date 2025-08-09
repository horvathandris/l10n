package io.github.horvathandris.localisation.generator

import io.github.horvathandris.localisation.generator.configuration.GeneratorConfiguration
import io.github.horvathandris.localisation.generator.configuration.GeneratorConfiguration.SimpleJavaConfiguration
import io.github.horvathandris.localisation.parser.MessageParser
import org.gradle.api.DefaultTask
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class GenerateMessagesTask : DefaultTask() {

    init {
        description = "Task to generate code for translation keys defined in a message bundle"
        group = BasePlugin.BUILD_GROUP
    }

    @get:Input
    abstract val messageBundleFile: Property<File>

    @get:Nested
    private var configuration: GeneratorConfiguration = SimpleJavaConfiguration()

    private val outputDirectory = project.layout.buildDirectory.dir("generated/sources/l10n").get()

    fun <C : GeneratorConfiguration> configuration(type: Class<C>, action: C.() -> Unit) {
        configuration = type.getDeclaredConstructor().newInstance().apply(action)
    }

    inline fun <reified C : GeneratorConfiguration> configuration(noinline action: C.() -> Unit) {
        configuration(C::class.java, action)
    }

    @TaskAction
    fun action() {
        cleanOutputDirectory()

        val messages = MessageParser().parse(messageBundleFile.get())

        val generator = GeneratorFactory.get(configuration)
        generator.generate(messages).forEach { writeOutputFile(it) }
    }

    private fun cleanOutputDirectory() {
        val generatedDir = outputDirectory.asFile
        if (generatedDir.exists()) {
            generatedDir.deleteRecursively()
        }
    }

    private fun writeOutputFile(generatorOutput: Generator.Output) {
        val packageDirectory = configuration.packageName.replace(".", "/")
        val outputFile = outputDirectory
            .file("main/java/$packageDirectory/${generatorOutput.filename}")
            .asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText(generatorOutput.content)
    }

}