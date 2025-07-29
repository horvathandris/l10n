package dev.horvathandris.localisation.generator

import dev.horvathandris.localisation.util.capitalise

private const val OUTPUT_FILENAME = "L10n.java"

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

    private fun generateContent(messages: MessageTree) = buildString {
        appendLine("package $packageName;")
        appendLine()
        appendLine("import dev.horvathandris.localisation.MessageKeyWithArgs;")
        appendLine("import javax.annotation.processing.Generated;")
        appendLine()
        appendLine("@Generated(")
        appendLine("${topLevelIndent}value = \"${this@SpringJavaGenerator.javaClass.canonicalName}\",")
        appendLine("${topLevelIndent}date = \"${java.time.OffsetDateTime.now()}\"")
        appendLine(")")
        appendLine("public final class L10n {")
        appendLine()
        appendLine("${topLevelIndent}private L10n() {}")
        appendMessages(messages, topLevelIndent)
        appendLine("}")
    }

    private fun StringBuilder.appendMessages(
        messages: MessageTree,
        indent: String
    ) {
        messages.forEach { (key, value) ->
            if (value.message?.value != null) {
                appendLine()
                appendLine("${indent}/**")
                appendLine("$indent * ${value.message.value}")
                appendLine("$indent */")
                appendLine("${indent}public static MessageKeyWithArgs ${key.lowercase()}(${formatFunctionArguments(value.message.arguments)}) {")
                appendLine("${indent}${topLevelIndent}return new MessageKeyWithArgs(\"${value.message.key}\"${formatCallArguments(value.message.arguments)});")
                appendLine("${indent}}")
            }

            if (value.children.isNotEmpty()) {
                appendLine()
                appendLine("${indent}public final class ${key.capitalise()} {")
                appendLine()
                appendLine("$indent${topLevelIndent}private ${key.capitalise()}() {}")
                appendMessages(value.children, "$indent$topLevelIndent")
                appendLine("${indent}}")
            }
        }
    }

    private fun formatFunctionArguments(arguments: List<String>): String =
        arguments.joinToString(separator = ", ") { "final String arg$it" }

    private fun formatCallArguments(arguments: List<String>): String =
        if (arguments.isNotEmpty()) arguments.joinToString(prefix = ", ", separator = ", ") { "arg$it" }
        else ""
}