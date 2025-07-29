package dev.horvathandris.localisation.generator

typealias OutputFileName = String
typealias OutputFileContent = String

abstract class Generator {

    abstract fun generate(messages: MessageTree): List<Output>

    enum class Type {
        SIMPLE,
        SPRING,
    }

    enum class Language(val value: String) {
        KOTLIN("kotlin"),
        JAVA("java"),
    }

    data class Output(
        val filename: OutputFileName,
        val content: OutputFileContent,
    )
}