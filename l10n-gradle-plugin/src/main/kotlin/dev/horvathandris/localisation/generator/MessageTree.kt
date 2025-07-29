package dev.horvathandris.localisation.generator

typealias MessageTree = Map<String, MessageComponent>

data class MessageComponent(
    val message: MessageValue? = null,
    val children: MutableMap<String, MessageComponent> = mutableMapOf(),
)

data class MessageValue(
    val key: String,
    val value: String? = null,
    val arguments: List<String> = emptyList(),
)