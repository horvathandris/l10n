package io.github.horvathandris.localisation.generator

import io.github.horvathandris.localisation.generator.builder.JavaSourceBuilder
import io.github.horvathandris.localisation.generator.configuration.GeneratorConfiguration.SimpleJavaConfiguration
import io.github.horvathandris.localisation.util.CodeCase
import io.github.horvathandris.localisation.util.convertCase

private const val TOP_LEVEL_CLASSNAME = "L10n"
private const val OUTPUT_FILENAME = "$TOP_LEVEL_CLASSNAME.java"

class SimpleJavaGenerator(
    configuration: SimpleJavaConfiguration,
) : Generator<SimpleJavaConfiguration>(configuration) {

    private val topLevelIndent: String = " ".repeat(configuration.indentSize)

    override fun generate(messages: MessageTree) = listOf(Output(
        filename = OUTPUT_FILENAME,
        content = generateContent(messages),
    ))

    private fun generateContent(messages: MessageTree): String {
        val topLevelClassBuilder = JavaSourceBuilder.ClassBuilder()
            .addAnnotation("""
                @Generated(
                ${topLevelIndent}value = "${this.javaClass.canonicalName}",
                ${topLevelIndent}date = "${java.time.OffsetDateTime.now()}"
                )
            """.trimIndent())
            .setName(TOP_LEVEL_CLASSNAME)
            .setModifiers("public final")
            .addConstructor("private")

        messages.forEach { (key, value) -> topLevelClassBuilder.buildContents(key, value) }

        return JavaSourceBuilder()
            .setPackageName(configuration.packageName)
            .addImport("javax.annotation.processing.Generated")
            .addClass(topLevelClassBuilder)
            .build(indentSize = topLevelIndent.length)
    }

    private fun JavaSourceBuilder.ClassBuilder.buildContents(key: String, value: MessageComponent): JavaSourceBuilder.ClassBuilder {
        if (value.message?.value != null) {
            this.addField("public static final String ${key.convertCase(CodeCase.SCREAMING_SNAKE)} = \"${value.message.key}\";")
        }

        if (value.children.isNotEmpty()) {
            val builder = JavaSourceBuilder.ClassBuilder()
                .setModifiers("public static final")
                .setName(key.convertCase(CodeCase.PASCAL))
                .addConstructor("private")
            this.addNestedClasses(
                value.children.map { (childKey, childValue) ->
                    builder.buildContents(childKey, childValue)
                }
            )
        }

        return this
    }
}
