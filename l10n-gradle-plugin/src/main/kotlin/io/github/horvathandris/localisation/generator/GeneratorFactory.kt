package io.github.horvathandris.localisation.generator

import io.github.horvathandris.localisation.generator.configuration.GeneratorConfiguration

abstract class GeneratorFactory {

    companion object {

        fun get(configuration: GeneratorConfiguration): Generator<out GeneratorConfiguration> {
            return when (configuration) {
                is GeneratorConfiguration.SimpleJavaConfiguration -> SimpleJavaGenerator(configuration)
                is GeneratorConfiguration.SpringJavaConfiguration -> SpringJavaGenerator(configuration)
            }
        }

    }

}