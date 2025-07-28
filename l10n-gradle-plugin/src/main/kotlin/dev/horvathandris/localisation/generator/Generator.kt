package dev.horvathandris.localisation.generator

typealias OutputFileName = String
typealias OutputFileContent = String

abstract class Generator {

    abstract fun generate(messages: MessageTree): GeneratorOutput
}

data class GeneratorOutput(
    val filename: OutputFileName,
    val content: OutputFileContent,
)