package io.github.horvathandris.localisation.generator

import io.github.horvathandris.localisation.generator.builder.JavaSourceBuilder
import io.github.horvathandris.localisation.util.CodeCase
import io.github.horvathandris.localisation.util.convertCase

private const val TOP_LEVEL_CLASSNAME = "L10n"
private const val OUTPUT_FILENAME = "$TOP_LEVEL_CLASSNAME.java"

class SpringJavaGenerator(
    val packageName: String,
    indentSize: Int,
): Generator() {

    private val topLevelIndent: String = " ".repeat(indentSize)

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
            .setPackageName(packageName)
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
                    .addBodyLine(
                        "return new L10nMessageSourceResolvable(\"${value.message.key}\", \"${value.message.value}\"${formatCallArguments(value.message.arguments)});"
                    )
                value.message.arguments.forEachIndexed { _, index ->
                    methodBuilder.addParameter("final String arg$index")
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

    private fun formatCallArguments(arguments: List<String>): String =
        if (arguments.isNotEmpty()) arguments.joinToString(prefix = ", ", separator = ", ") { "arg$it" }
        else ""
}
