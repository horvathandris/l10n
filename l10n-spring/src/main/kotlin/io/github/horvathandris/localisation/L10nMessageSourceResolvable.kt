package io.github.horvathandris.localisation

import org.springframework.context.support.DefaultMessageSourceResolvable

/**
 * A class that extends `DefaultMessageSourceResolvable` to represent a localised message source resolvable.
 * It has a more convenient constructor for creating message resolvables with a code, default message and arguments.
 *
 * @param code The code of the message to be resolved.
 * @param defaultMessage The default message to be used if the code is not found.
 * @param args Optional arguments to be used in the message formatting.
 */
class L10nMessageSourceResolvable(
    code: String,
    defaultMessage: String,
    vararg args: String = emptyArray(),
) : DefaultMessageSourceResolvable(
    arrayOf(code),
    args,
    defaultMessage,
)
