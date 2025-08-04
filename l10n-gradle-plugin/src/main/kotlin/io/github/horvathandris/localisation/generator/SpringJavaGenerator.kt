package io.github.horvathandris.localisation.generator

import io.github.horvathandris.localisation.util.CodeCase
import io.github.horvathandris.localisation.util.convertCase

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
        appendLine("import io.github.horvathandris.localisation.L10nMessageSourceResolvable;")
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
                appendLine("${indent}public static L10nMessageSourceResolvable ${key.convertCase(CodeCase.CAMEL)}(${formatFunctionArguments(value.message.arguments)}) {")
                appendLine("${indent}${topLevelIndent}return new L10nMessageSourceResolvable(\"${value.message.key}\", \"${value.message.value}\"${formatCallArguments(value.message.arguments)});")
                appendLine("${indent}}")
            }

            if (value.children.isNotEmpty()) {
                appendLine()
                appendLine("${indent}public final class ${key.convertCase(CodeCase.PASCAL)} {")
                appendLine()
                appendLine("$indent${topLevelIndent}private ${key.convertCase(CodeCase.PASCAL)}() {}")
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