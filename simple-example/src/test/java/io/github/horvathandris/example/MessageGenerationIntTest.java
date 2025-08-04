package io.github.horvathandris.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.horvathandris.example.l10n.L10n;
import org.junit.jupiter.api.Test;

public class MessageGenerationIntTest {

  @Test
  void generated_messages_are_valid() {
    assertEquals("test.message.title", L10n.Test.Message.TITLE);
  }
}
