package dev.horvathandris.example;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import dev.horvathandris.example.l10n.L10n;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class L10nConfig {

  @Bean
  public MessageSource messageSource() {
    final var messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("i18n/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean
  public L10n l10n(MessageSource messageSource) {
    return new L10n(messageSource);
  }
}
