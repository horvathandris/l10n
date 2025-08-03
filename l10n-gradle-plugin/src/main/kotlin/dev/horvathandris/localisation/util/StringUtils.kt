package dev.horvathandris.localisation.util

fun String.capitalise() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase() else it.toString()
}

enum class CodeCase {
    PASCAL,
    CAMEL,
}

fun String.convertCase(toCase: CodeCase) =
    when (toCase) {
      CodeCase.PASCAL -> split("-", "_").joinToString("") { it.capitalise() }
      CodeCase.CAMEL -> split("-", "_")
          .mapIndexed { index, value -> if (index == 0) value else value.capitalise() }
          .joinToString("")
    }