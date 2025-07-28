package dev.horvathandris.localisation.generator

typealias OutputFileName = String
typealias OutputFileContent = String

abstract class Generator {

    abstract fun generate(messages: MessageTree): Output

    enum class Type {
        SIMPLE,
    }

    enum class Language {
        KOTLIN,
        JAVA,
    }

    data class Output(
        val filename: OutputFileName,
        val content: OutputFileContent,
    )
}