package io.github.horvathandris.localisation

import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceResolvable
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

/**
 * A class that provides translation functionality using a `MessageSource`.
 * It allows for translating messages based on the current locale or a specified locale.
 *
 * @property messageSource The `MessageSource` used for resolving messages.
 */
open class Translator(
    private val messageSource: MessageSource,
) {

    fun translate(message: MessageSourceResolvable): String {
        return messageSource.getMessage(message, LocaleContextHolder.getLocale())
    }

    fun translate(message: MessageSourceResolvable, locale: Locale): String {
        return messageSource.getMessage(message, locale)
    }

}