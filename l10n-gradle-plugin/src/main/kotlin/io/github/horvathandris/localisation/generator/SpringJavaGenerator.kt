package io.github.horvathandris.localisation.generator

import io.github.horvathandris.localisation.generator.builder.JavaSourceBuilder
import io.github.horvathandris.localisation.generator.configuration.GeneratorConfiguration.SpringJavaConfiguration
import io.github.horvathandris.localisation.util.CodeCase
import io.github.horvathandris.localisation.util.convertCase

private const val TOP_LEVEL_CLASSNAME = "L10n"
private const val OUTPUT_FILENAME = "$TOP_LEVEL_CLASSNAME.java"

class SpringJavaGenerator(
    configuration: SpringJavaConfiguration,
): Generator<SpringJavaConfiguration>(configuration) {

    private val topLevelIndent: String = " ".repeat(configuration.indentSize)

    override fun generate(messages: MessageTree) = listOf(
        Output(
            filename = OUTPUT_FILENAME,
            content = generateContent(messages),
        ),
    )

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
            .addMessagesToClass(messages)

        return JavaSourceBuilder()
            .setPackageName(configuration.packageName)
            .addImport("io.github.horvathandris.localisation.L10nMessageSourceResolvable")
            .addImport("javax.annotation.processing.Generated")
            .addClass(topLevelClassBuilder)
            .build(indentSize = topLevelIndent.length)
    }

    private fun JavaSourceBuilder.ClassBuilder.addMessagesToClass(
        messages: MessageTree
    ): JavaSourceBuilder.ClassBuilder {
        messages.forEach { (key, value) ->
            // Add method for value
            if (value.message?.value != null) {
                val methodBuilder = JavaSourceBuilder.MethodBuilder()
                    .addJavadocLine(value.message.value)
                    .setModifiers("public static")
                    .setName(key.convertCase(CodeCase.CAMEL))
                    .setReturnType("L10nMessageSourceResolvable")
                    .addBodyLines(
                        "return new L10nMessageSourceResolvable(\"${value.message.key}\", \"${value.message.value}\"${joinCallArguments(value.message.arguments)});",
                    )
                decorateMethodArguments(value.message.arguments).forEach {
                    methodBuilder.addParameter(it)
                }
                this.addMethod(methodBuilder)
            }
            // Add class for children
            if (value.children.isNotEmpty()) {
                val nestedClass = JavaSourceBuilder.ClassBuilder()
                    .setModifiers("public static final")
                    .setName(key.convertCase(CodeCase.PASCAL))
                    .addConstructor("private")
                nestedClass.addMessagesToClass(value.children)
                this.addNestedClasses(nestedClass)
            }
        }
        return this
    }

    private fun formatArguments(arguments: List<Argument>): List<String> =
        arguments.map {
            if (configuration.useFormatAsArgumentName && !it.format.isNullOrBlank()) {
                it.format
            } else {
                "arg${it.index}"
            }
        }

    private fun joinCallArguments(arguments: List<Argument>): String =
        formatArguments(arguments)
            .ifEmpty { return "" }
            .joinToString(prefix = ", ", separator = ",")

    private fun decorateMethodArguments(arguments: List<Argument>): List<String> =
        formatArguments(arguments).map { "final String $it" }
}
