package io.github.horvathandris.localisation.generator.builder

import java.util.UUID

class JavaSourceBuilder {

    private var packageName: String? = null
    private val imports = mutableListOf<String>()
    private val classes = mutableListOf<ClassBuilder>()

    fun setPackageName(packageName: String): JavaSourceBuilder {
        this.packageName = packageName
        return this
    }

    fun addImport(import: String): JavaSourceBuilder {
        imports += import
        return this
    }

    fun addClass(classBuilder: ClassBuilder): JavaSourceBuilder {
        classes += classBuilder
        return this
    }

    fun build(indentSize: Int): String = buildString {

        if (!packageName.isNullOrBlank()) {
            appendLine("package ${packageName};")
            appendLine()
        }

        imports.sorted().forEach { import -> appendLine("import $import;") }
        appendLine()

        classes.forEach { appendLine(it.build(indentSize, 0)) }
    }

    class MethodBuilder {

        private var javadocLines = mutableListOf<String>()
        private var name: String? = null
        private var returnType: String = "void"
        private var modifiers: String = "public"
        private val parameters = mutableListOf<String>()
        private val bodyLines = mutableListOf<String>()

        fun addJavadocLine(line: String): MethodBuilder {
            javadocLines += line
            return this
        }

        fun setName(name: String): MethodBuilder {
            this.name = name
            return this
        }

        fun setReturnType(type: String): MethodBuilder {
            this.returnType = type
            return this
        }

        fun setModifiers(modifiers: String): MethodBuilder {
            this.modifiers = modifiers
            return this
        }

        fun addParameter(parameter: String): MethodBuilder {
            parameters += parameter
            return this
        }

        fun addBodyLines(vararg lines: String): MethodBuilder {
            bodyLines += lines
            return this
        }

        fun build(indentSize: Int, level: Int): String = buildString {
            val indent = " ".repeat(indentSize * level)
            if (javadocLines.isNotEmpty()) {
                appendLine("$indent/**")
                javadocLines.forEach { appendLine("$indent * $it") }
                appendLine("$indent */")
            }
            val paramList = parameters.joinToString(", ") { it }
            appendLine("$indent$modifiers $returnType $name($paramList) {")
            val bodyIndent = " ".repeat(indentSize * (level + 1))
            bodyLines.forEach { appendLine("$bodyIndent$it") }
            appendLine("$indent}")
        }
    }

    class ClassBuilder {

        private val annotations = mutableListOf<String>()
        private var modifiers: String = "public"
        private var name: String = "UnnamedClass_${UUID.randomUUID().toString().take(8)}"
        private val fields = mutableListOf<String>()
        private val methods = mutableListOf<MethodBuilder>()
        private val nestedClasses = mutableListOf<ClassBuilder>()

        fun addAnnotation(annotation: String): ClassBuilder {
            annotations += annotation
            return this
        }

        fun setName(name: String): ClassBuilder {
            this.name = name
            return this
        }

        fun addField(field: String): ClassBuilder {
            fields += field
            return this
        }

        fun addMethod(method: MethodBuilder): ClassBuilder {
            methods += method
            return this
        }

        fun addConstructor(modifiers: String): ClassBuilder {
            return addMethod(
                MethodBuilder()
                    .setName(name)
                    .setReturnType("")
                    .setModifiers(modifiers)
            )
        }

        fun addNestedClasses(nestedClasses: Collection<ClassBuilder>): ClassBuilder {
            this.nestedClasses += nestedClasses
            return this
        }

        fun addNestedClasses(vararg nestedClasses: ClassBuilder): ClassBuilder {
            this.nestedClasses += nestedClasses
            return this
        }

        fun setModifiers(modifiers: String): ClassBuilder {
            this.modifiers = modifiers
            return this
        }

        fun build(indentSize: Int, level: Int): String = buildString {
            val indent = " ".repeat(indentSize * level)
            annotations.forEach { appendLine(it) }
            appendLine("${indent}${modifiers} class $name {")
            val bodyIndent = " ".repeat(indentSize * (level + 1))
            fields.forEach { appendLine("$bodyIndent$it") }
            methods.forEach { append(it.build(indentSize, level + 1)) }
            nestedClasses.forEach { append(it.build(indentSize, level + 1)) }
            appendLine("$indent}")
        }
    }
}