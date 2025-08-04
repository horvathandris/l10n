package io.github.horvathandris.localisation.util

fun String.capitalise() = replaceFirstChar {
  if (it.isLowerCase()) it.titlecase() else it.toString()
}

enum class CodeCase {
  PASCAL,
  CAMEL,
  SCREAMING_SNAKE,
}

fun String.convertCase(toCase: CodeCase) =
  when (toCase) {
    CodeCase.PASCAL -> split("-", "_").joinToString("") { it.capitalise() }
    CodeCase.CAMEL -> split("-", "_")
        .mapIndexed { index, value -> if (index == 0) value else value.capitalise() }
        .joinToString("")
    CodeCase.SCREAMING_SNAKE -> split("-", "_").joinToString("_") { it.uppercase() }
  }