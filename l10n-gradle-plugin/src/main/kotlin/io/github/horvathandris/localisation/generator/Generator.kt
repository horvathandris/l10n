package io.github.horvathandris.localisation.generator

import io.github.horvathandris.localisation.generator.configuration.GeneratorConfiguration

typealias OutputFileName = String
typealias OutputFileContent = String

abstract class Generator<C : GeneratorConfiguration>(
    val configuration: C,
) {

    abstract fun generate(messages: MessageTree): List<Output>

    data class Output(
        val filename: OutputFileName,
        val content: OutputFileContent,
    )
}