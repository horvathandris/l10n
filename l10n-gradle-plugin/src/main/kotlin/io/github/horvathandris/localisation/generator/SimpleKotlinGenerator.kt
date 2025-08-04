package io.github.horvathandris.localisation.generator

import io.github.horvathandris.localisation.util.CodeCase
import io.github.horvathandris.localisation.util.convertCase

private const val TOP_LEVEL_CLASSNAME = "L10n"
private const val OUTPUT_FILENAME = "$TOP_LEVEL_CLASSNAME.kt"

class SimpleKotlinGenerator(
    val packageName: String,
    indentSize: Int,
) : Generator() {

    private val topLevelIndent: String = " ".repeat(indentSize)

    override fun generate(messages: MessageTree) = listOf(Output(
        filename = OUTPUT_FILENAME,
        content = generateContent(messages),
    ))

    private fun generateContent(messages: MessageTree) = buildString {
        appendLine("package $packageName")
        appendLine()
        appendLine("object L10n {")
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
                appendLine("${indent}const val ${key.convertCase(CodeCase.SCREAMING_SNAKE)} = \"${value.message.key}\"")
            }

            if (value.children.isNotEmpty()) {
                appendLine()
                appendLine("${indent}object ${key.convertCase(CodeCase.PASCAL)} {")
                appendMessages(value.children, "$indent$topLevelIndent")
                appendLine("${indent}}")
            }
        }
    }
}