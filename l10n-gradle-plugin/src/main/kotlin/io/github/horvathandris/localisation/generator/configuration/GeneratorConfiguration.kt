package io.github.horvathandris.localisation.generator.configuration

import org.gradle.api.tasks.Input

sealed class GeneratorConfiguration {

    @Input
    var packageName: String = "io.github.horvathandris.localisation.generated"
    @Input
    var indentSize: Int = 4

    class SimpleJavaConfiguration : GeneratorConfiguration()

    class SimpleKotlinConfiguration : GeneratorConfiguration()

    class SpringJavaConfiguration(
        @Input
        var useFormatAsArgumentName: Boolean = false,
    ) : GeneratorConfiguration()
}