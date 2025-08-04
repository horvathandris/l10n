package io.github.horvathandris.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.horvathandris.localisation.Translator;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.horvathandris.example.l10n.L10n;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

@SpringBootTest
public class MessageGenerationIntTest {

  @Autowired
  Translator translator;

  @Test
  void generated_messages_are_valid() {
    assertEquals("test.message.title", L10n.Test.Message.title("1", "2").getCode());
    assertEquals("Test message title with 1 and 2", translator.translate(L10n.Test.Message.title("1", "2")));

    assertEquals("Detroit, Chicago, Los Angeles", translator.translate(L10n.MultiLine.Value.content()));
  }

  @Test
  void generated_messages_can_be_translated_using_context_holder() {
    LocaleContextHolder.setLocale(Locale.of("hu"));
    assertEquals("test.message.title", L10n.Test.Message.title("1", "2").getCode());
    assertEquals(
      "Teszt üzenet fejléc 1 és 2 használatával",
      translator.translate(L10n.Test.Message.title("1", "2"))
    );
  }

  @Test
  void generated_messages_can_be_translated_using_argument() {
    assertEquals("test.message.title", L10n.Test.Message.title("1", "2").getCode());
    assertEquals(
      "Teszt üzenet fejléc 1 és 2 használatával",
      translator.translate(L10n.Test.Message.title("1", "2"), Locale.of("hu"))
    );
  }

  @Test
  void generated_messages_provide_default_when_locale_not_found() {
    assertEquals("test.message.title", L10n.Test.Message.title("1", "2").getCode());
    assertEquals(
      "Test message title with 1 and 2",
      translator.translate(L10n.Test.Message.title("1", "2"), Locale.of("£$"))
    );
  }
}
