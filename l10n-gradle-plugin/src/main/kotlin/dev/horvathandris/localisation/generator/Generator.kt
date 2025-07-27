package dev.horvathandris.localisation.generator

abstract class Generator {

    abstract fun generate(messages: MessageTree): String
}