package dev.horvathandris.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.horvathandris.localisation.Translator;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import dev.horvathandris.example.l10n.L10n;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

@SpringBootTest
public class MessageGenerationIntTest {

  @Autowired
  Translator translator;

  @Test
  void generated_messages_are_valid() {
    assertEquals("test.message.title", L10n.Test.Message.title("first", "second").getKey());
    assertEquals("Test message title with first and second", translator.translate(L10n.Test.Message.title("first", "second")));
  }

  @Test
  void generated_messages_can_be_translated() {
    LocaleContextHolder.setLocale(Locale.of("hu"));
    assertEquals("test.message.title", L10n.Test.Message.title("first", "second").getKey());
    assertEquals("Teszt üzenet fejléc első és második használatával", translator.translate(L10n.Test.Message.title("first", "second")));
  }
}
