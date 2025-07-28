package dev.horvathandris.localisation.generator

import dev.horvathandris.localisation.util.capitalise

private const val OUTPUT_FILENAME = "L10n.java"

class SpringJavaGenerator(
    val packageName: String,
    indentSize: Int,
): Generator() {

    private val topLevelIndent: String = " ".repeat(indentSize)

    override fun generate(messages: MessageTree) = Output(
        filename = OUTPUT_FILENAME,
        content = generateContent(messages),
    )

    private fun generateContent(messages: MessageTree) = buildString {
        appendLine("package $packageName;")
        appendLine()
        appendLine("import org.springframework.context.MessageSource;")
        appendLine("import org.springframework.context.i18n.LocaleContextHolder;")
        appendLine("import org.springframework.stereotype.Component;")
        appendLine()
        appendLine("@Component")
        appendLine("public final class L10n {")
        appendLine()
        appendLine("${topLevelIndent}private final MessageSource messageSource;")
        appendLine()
        appendLine("${topLevelIndent}public L10n(final MessageSource messageSource) {")
        appendLine("${topLevelIndent}${topLevelIndent}this.messageSource = messageSource;")
        appendLine("${topLevelIndent}}")
        appendLine()
        appendLine("${topLevelIndent}public String getMessage(final String key) {")
        appendLine("${topLevelIndent}${topLevelIndent}return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());")
        appendLine("${topLevelIndent}}")
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
                appendLine("${indent}public final class ${key.capitalise()} {")
                appendLine()
                appendLine("$indent${topLevelIndent}private ${key.capitalise()}() {}")
                appendMessages(value.children, "$indent$topLevelIndent")
                appendLine("${indent}}")
            }
        }
    }
}