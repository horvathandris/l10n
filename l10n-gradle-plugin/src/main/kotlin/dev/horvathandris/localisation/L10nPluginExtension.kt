package dev.horvathandris.localisation

import org.gradle.api.tasks.Input

abstract class L10nPluginExtension {

    @Input
    val messageBundlePath: String = "/src/main/resources/messages.properties"
}