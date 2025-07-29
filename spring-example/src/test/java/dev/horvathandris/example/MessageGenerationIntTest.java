package dev.horvathandris.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.horvathandris.example.l10n.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import dev.horvathandris.example.l10n.L10n;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = L10nConfig.class)
public class MessageGenerationIntTest {

  @Autowired
  Translator translator;

  @Test
  void generated_messages_are_valid() {
    assertEquals("test.message.title", L10n.Test.Message.title("first", "second").key());
    assertEquals("Test message title with first and second", translator.translate(L10n.Test.Message.title("first", "second")));
  }
}
