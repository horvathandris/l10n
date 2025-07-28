package dev.horvathandris.localisation.util

fun String.capitalise() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase() else it.toString()
}