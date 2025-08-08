package io.github.horvathandris.localisation.generator.builder

class JavaSourceBuilder {

    private var packageName: String? = null
    private var imports: List<String> = listOf()

    fun setPackageName(packageName: String): JavaSourceBuilder {
        this.packageName = packageName
        return this
    }

    fun addImport(import: String): JavaSourceBuilder {
        imports += import
        return this
    }

    fun build(indentSize: Int): String = buildString {
        val topLevelIndent = " ".repeat(indentSize)

        if (!packageName.isNullOrBlank()) {
            appendLine("package ${packageName};")
            appendLine()
        }

        imports.forEach { import -> appendLine("import $import;") }
        appendLine()

        appendLine("public final class L10n {")
        appendLine("${topLevelIndent}private L10n() {}")
        appendLine("}")
    }
}