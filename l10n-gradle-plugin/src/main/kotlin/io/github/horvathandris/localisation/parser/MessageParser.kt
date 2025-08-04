package io.github.horvathandris.localisation.parser

import io.github.horvathandris.localisation.generator.MessageComponent
import io.github.horvathandris.localisation.generator.MessageTree
import io.github.horvathandris.localisation.generator.MessageValue
import java.io.File
import java.util.Properties

class MessageParser {

  fun parse(file: File): MessageTree {
    val properties = Properties()
    file.reader().use { properties.load(it) }
    return transformToMessageTree(properties)
  }

  private fun transformToMessageTree(properties: Properties): MessageTree {
    return properties.entries
      .map { (key, value) -> key.toString() to value.toString() }
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
  }

  private fun parseArguments(value: String): List<String> =
    """\{(\d+)}""".toRegex()
      .findAll(value)
      .map { it.groupValues[1] }
      .toList()

}