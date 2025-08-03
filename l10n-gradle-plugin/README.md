# l10n-gradle-plugin

A plugin to autogenerate Java/Kotlin code from i18n `messages.properties` files.

## Installation

```kotlin
// build.gradle.kts

plugins {
  id("l10n-gradle-plugin")
}
```

## Configuration

```kotlin
// build.gradle.kts

tasks.named<GenerateMessagesTask>("generateMessages") {
  messageBundleFile = file("src/main/resources/messages.properties")
  packageName = "com.example.l10n"
}
```

| Property          | Description                                                                         | Required | Default                   |
|-------------------|-------------------------------------------------------------------------------------|----------|---------------------------|
| messageBundleFile | The .properties file with the message codes to generate code from.                  | Yes      |                           |
| packageName       | Which package the code should be generated to.                                      | Yes      |                           |
| type              | The type of the generator. For values see `Generator.Type`.                         | No       | `Generator.Type.SIMPLE`   |
| language          | The programming language of the generated code. For values see `Generator.Language` | No       | `Generator.Language.JAVA` |

