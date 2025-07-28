package dev.horvathandris.localisation.generator

import dev.horvathandris.localisation.util.capitalise

class SimpleJavaGenerator(
    val packageName: String,
    indentSize: Int
) : Generator() {

    private val topLevelIndent: String = " ".repeat(indentSize)

    override fun generate(messages: MessageTree) = buildString {
        appendLine("package $packageName;")
        appendLine()
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
            if (value.value != null) {
                appendLine()
                appendLine("${indent}/**")
                appendLine("$indent * ${value.value}")
                appendLine("$indent */")
                appendLine("${indent}public static final String ${key.uppercase()} = \"${value.key}\";")
            }

            if (value.children.isNotEmpty()) {
                appendLine()
                appendLine("${indent}public static final class ${key.capitalise()} {")
                appendLine()
                appendLine("$indent${topLevelIndent}private ${key.capitalise()}() {}")
                appendMessages(value.children, "$indent$topLevelIndent")
                appendLine("${indent}}")
            }
        }
    }
}