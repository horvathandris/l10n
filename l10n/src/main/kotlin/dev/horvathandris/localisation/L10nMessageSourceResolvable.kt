package dev.horvathandris.localisation

import org.springframework.context.support.DefaultMessageSourceResolvable

class L10nMessageSourceResolvable(
    code: String,
    defaultMessage: String,
    vararg args: String = emptyArray()
) : DefaultMessageSourceResolvable(
    arrayOf(code),
    args,
    defaultMessage,
)
