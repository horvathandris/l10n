package dev.horvathandris.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import dev.horvathandris.example.l10n.L10n;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = L10nConfig.class)
public class MessageGenerationIntTest {

  @Autowired
  L10n l10n;

  @Test
  void generated_messages_are_valid() {
    assertEquals("test.message.title", L10n.Test.Message.TITLE);
    assertEquals("Test message title", l10n.getMessage(L10n.Test.Message.TITLE));
  }
}
