package io.github.horvathandris.localisation

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

class Translator(
    private val messageSource: MessageSource,
) {

    fun translate(message: L10nMessageSourceResolvable): String {
        return messageSource.getMessage(message, LocaleContextHolder.getLocale())
    }

    fun translate(message: L10nMessageSourceResolvable, locale: Locale): String {
        return messageSource.getMessage(message, locale)
    }

}