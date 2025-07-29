package dev.horvathandris.localisation.autoconfigure

import dev.horvathandris.localisation.Translator
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConditionalOnBean(MessageSource::class)
open class L10nAutoConfiguration {

    @Bean
    open fun translator(messageSource: MessageSource): Translator {
        return Translator(messageSource)
    }
}