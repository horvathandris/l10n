package dev.horvathandris.localisation

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

class Translator(
    private val messageSource: MessageSource,
) {

    fun translate(message: MessageKeyWithArgs): String {
        return messageSource.getMessage(message.key, message.args, LocaleContextHolder.getLocale())
    }

}