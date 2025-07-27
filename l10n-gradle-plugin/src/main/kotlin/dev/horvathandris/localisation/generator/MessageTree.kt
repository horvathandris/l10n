package dev.horvathandris.localisation.generator

typealias MessageTree = Map<String, MessageComponent>

data class MessageComponent(
    val key: String? = null,
    val value: String? = null,
    val children: MutableMap<String, MessageComponent> = mutableMapOf(),
)