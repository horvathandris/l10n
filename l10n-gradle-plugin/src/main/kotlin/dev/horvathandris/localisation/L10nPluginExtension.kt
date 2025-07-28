package dev.horvathandris.localisation

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import java.io.File

abstract class L10nPluginExtension {

    @get:Input
    abstract val messageBundleFile: Property<File>
}