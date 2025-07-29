package dev.horvathandris.localisation.generator

import dev.horvathandris.localisation.util.capitalise

private const val TOP_LEVEL_CLASSNAME = "L10n"
private const val OUTPUT_FILENAME = "$TOP_LEVEL_CLASSNAME.java"

class SimpleJavaGenerator(
    val packageName: String,
    indentSize: Int,
) : Generator() {

    private val topLevelIndent: String = " ".repeat(indentSize)

    override fun generate(messages: MessageTree) = listOf(Output(
        filename = OUTPUT_FILENAME,
        content = generateContent(messages),
    ))

    private fun generateContent(messages: MessageTree) = buildString {
        appendLine("package $packageName;")
        appendLine()
        appendLine("import javax.annotation.processing.Generated;")
        appendLine()
        appendLine("@Generated(\"${this@SimpleJavaGenerator.javaClass.canonicalName}\")")
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