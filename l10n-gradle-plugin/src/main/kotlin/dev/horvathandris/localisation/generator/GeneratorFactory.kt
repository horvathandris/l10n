package dev.horvathandris.localisation.generator

abstract class GeneratorFactory {

    companion object {

        fun get(
            type: Generator.Type,
            language: Generator.Language,
            packageName: String,
            indentSize: Int,
        ): Generator {
            return when (type) {
                Generator.Type.SIMPLE -> getSimpleGenerator(language, packageName, indentSize)
                Generator.Type.SPRING -> getSpringGenerator(language, packageName, indentSize)
            }
        }

        private fun getSimpleGenerator(
            language: Generator.Language,
            packageName: String,
            indentSize: Int,
        ): Generator {
            return when (language) {
                Generator.Language.JAVA -> SimpleJavaGenerator(packageName, indentSize)
                Generator.Language.KOTLIN -> SimpleKotlinGenerator(packageName, indentSize)
            }
        }

        private fun getSpringGenerator(
            language: Generator.Language,
            packageName: String,
            indentSize: Int,
        ): Generator {
            return when (language) {
                Generator.Language.JAVA -> SpringJavaGenerator(packageName, indentSize)
                Generator.Language.KOTLIN -> TODO()
            }
        }
    }

}