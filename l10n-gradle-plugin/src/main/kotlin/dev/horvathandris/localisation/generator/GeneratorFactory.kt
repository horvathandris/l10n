package dev.horvathandris.localisation.generator

abstract class GeneratorFactory {

    companion object {

        fun get(
            type: GeneratorType,
            language: GeneratorLanguage,
            packageName: String,
            indentSize: Int,
        ): Generator {
            return when (type) {
                GeneratorType.SIMPLE -> getSimpleGenerator(language, packageName, indentSize)
            }
        }

        private fun getSimpleGenerator(
            language: GeneratorLanguage,
            packageName: String,
            indentSize: Int,
        ): Generator {
            return when (language) {
                GeneratorLanguage.JAVA -> SimpleJavaGenerator(packageName, indentSize)
                GeneratorLanguage.KOTLIN -> TODO()
            }
        }
    }

}