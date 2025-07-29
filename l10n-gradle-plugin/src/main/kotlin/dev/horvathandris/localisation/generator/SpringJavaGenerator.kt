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
        Output(
            filename = "Translator.java",
            content = generateTranslatorService(),
        ),
    )

    private fun generateContent(messages: MessageTree) = buildString {
        appendLine("package $packageName;")
        appendLine()
        appendLine("import javax.annotation.processing.Generated;")
        appendLine()
        appendLine("@Generated(\"${this@SpringJavaGenerator.javaClass.canonicalName}\")")
        appendLine("public final class L10n {")
        appendLine()
        appendLine("${topLevelIndent}private L10n() {}")
        appendMessages(messages, topLevelIndent)
        appendLine()
        appendLine("${topLevelIndent}public record MessageKeyWithArgs(String key, Object... args) {}")
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
                appendLine("${indent}public static MessageKeyWithArgs ${key.lowercase()}(Object... args) {")
                appendLine("${indent}${topLevelIndent}return new MessageKeyWithArgs(\"${value.key}\", args);")
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

    private fun generateTranslatorService() = buildString {
        appendLine("package $packageName;")
        appendLine()
        appendLine("import javax.annotation.processing.Generated;")
        appendLine("import org.springframework.context.MessageSource;")
        appendLine("import org.springframework.context.i18n.LocaleContextHolder;")
        appendLine("import org.springframework.stereotype.Component;")
        appendLine()
        appendLine("@Generated(\"${this@SpringJavaGenerator.javaClass.canonicalName}\")")
        appendLine("@Component")
        appendLine("public final class Translator {")
        appendLine()
        appendLine("${topLevelIndent}private final MessageSource messageSource;")
        appendLine()
        appendLine("${topLevelIndent}public Translator(final MessageSource messageSource) {")
        appendLine("${topLevelIndent}${topLevelIndent}this.messageSource = messageSource;")
        appendLine("${topLevelIndent}}")
        appendLine()
        appendLine("${topLevelIndent}public String translate(final L10n.MessageKeyWithArgs message) {")
        appendLine("${topLevelIndent}${topLevelIndent}return messageSource.getMessage(message.key(), message.args(), LocaleContextHolder.getLocale());")
        appendLine("${topLevelIndent}}")
        appendLine("}")
        appendLine()
    }
}