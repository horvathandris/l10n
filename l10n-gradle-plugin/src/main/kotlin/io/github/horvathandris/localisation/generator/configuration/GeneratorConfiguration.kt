package io.github.horvathandris.localisation.generator.configuration

import org.gradle.api.tasks.Input

sealed class GeneratorConfiguration {

    @Input
    var packageName: String = "io.github.horvathandris.localisation.generated"
    @Input
    var indentSize: Int = 4

    class SimpleJavaConfiguration : GeneratorConfiguration()

    class SpringJavaConfiguration(
        /**
         * Whether to use the format of the message as the argument name in the generated method.
         * If false, the argument name will be "arg0", "arg1", etc.
         *
         * Example usage:
         * greeting=Hello, {0,,name}!
         */
        @Input
        var useFormatAsArgumentName: Boolean = false,
    ) : GeneratorConfiguration()
}