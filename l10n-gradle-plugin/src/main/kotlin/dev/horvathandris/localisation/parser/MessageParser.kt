package dev.horvathandris.localisation.parser

import dev.horvathandris.localisation.generator.MessageComponent
import java.io.File
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

class MessageParser(private val file: File) {

    fun parse() = file
        .readLines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .filter { !it.startsWith("#") && !it.startsWith("!") }
        .map { it.split("=") }
        .map { (key, value) -> key.trimEnd() to value.trimStart() }
        .fold(mutableMapOf<String, MessageComponent>()) { messages, (key, value) ->
            val keyComponents = key.split(".")
            var currentNode = messages
            for (component in keyComponents.dropLast(1)) {
                currentNode = currentNode.computeIfAbsent(component) {
                    MessageComponent()
                }.children
            }
            currentNode[keyComponents.last()] = MessageComponent(key, value)
            messages
        }
}