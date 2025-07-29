package dev.horvathandris.localisation.parser

import dev.horvathandris.localisation.generator.MessageComponent
import dev.horvathandris.localisation.generator.MessageTree
import dev.horvathandris.localisation.generator.MessageValue
import java.io.File
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

class MessageParser(private val file: File) {

    fun parse(): MessageTree = file
        .readLines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .filter { !it.startsWith("#") && !it.startsWith("!") }
        .map { it.split("=") }
        .map { (key, value) -> key.trimEnd() to value.trimStart() }
        .map { (key, value) -> MessageValue(key, value, parseArguments(value)) }
        .fold(mutableMapOf()) { messages, message ->
            val keyComponents = message.key.split(".")
            var currentNode = messages
            for (component in keyComponents.dropLast(1)) {
                currentNode = currentNode.computeIfAbsent(component) {
                    MessageComponent()
                }.children
            }
            currentNode[keyComponents.last()] = MessageComponent(message)
            messages
        }

    private fun parseArguments(value: String): List<String> =
        """\{(\d+)}""".toRegex()
            .findAll(value)
            .map { it.groupValues[1] }
            .toList()
}