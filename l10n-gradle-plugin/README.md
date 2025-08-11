# l10n-gradle-plugin

A plugin to autogenerate Java/Kotlin code from i18n `messages.properties` files.

## Installation

```kotlin
// build.gradle.kts

plugins {
  id("io.github.horvathandris.localisation") version("0.0.+")
}
```

## Configuration

```kotlin
// build.gradle.kts

tasks.named<GenerateMessagesTask>("generateMessages") {
  messageBundleFile = file("src/main/resources/messages.properties")
  generatorConfig<SimpleJavaGeneratorConfig> {
    packageName = "com.example.l10n"
  }
}
```

| Property          | Description                                                                         | Required | Default |
|-------------------|-------------------------------------------------------------------------------------|----------|---------|
| messageBundleFile | The .properties file with the message codes to generate code from.                  | Yes      | N/A     |

The generatorConfig block is used to configure the code generator. The following generators are available:

### SimpleJavaGeneratorConfig

Generates simple Java nested classes with static final fields for each message code.

A sample project would look like this:
```kotlin
// build.gradle.kts
tasks.named<GenerateMessagesTask>("generateMessages") {
    messageBundleFile = file("src/main/resources/messages.properties")
    generatorConfig<SimpleJavaGeneratorConfig> {
        packageName = "com.example.l10n"
    }
}
```

```properties
# src/main/resources/messages.properties
some.example.message=This is a message
```

```java
// build/generated/sources/l10n/com/example/l10n/L10n.java
package com.example.l10n;

import javax.annotation.processing.Generated;

@Generated(
  value = "io.github.horvathandris.localisation.generator.SimpleJavaGenerator",
  date = "2025-08-11T14:59:24.795917+01:00"
)
public final class L10n {
    private  L10n() {
    }
    public static final class Some {
        private  Some() {
        }
        public static final class Example {
            public static final String MESSAGE = "some.example.message";
            private  Example() {
            }
        }
    }
}
```

Which can be used in your code like this:

```java
import com.example.l10n.L10n;

public class Example {
    public void printMessage() {
        String code = L10n.Some.Example.MESSAGE;
        // use the code to translate the message
    }
}
```


### SpringJavaGeneratorConfig

Generates nested Java classes with static methods for each message code, with arguments support, suitable for Spring applications.

A sample project would look like this:
```kotlin
// build.gradle.kts
tasks.named<GenerateMessagesTask>("generateMessages") {
    messageBundleFile = file("src/main/resources/messages.properties")
    generatorConfig<SpringJavaGeneratorConfig> {
        packageName = "com.example.l10n"
        useFormatAsArgumentName = true
    }
}
```

```properties
# src/main/resources/messages.properties
some.example.message=This is a message with {0,,first} argument
```

```java
// build/generated/sources/l10n/com/example/l10n/L10n.java
package com.example.l10n;

import io.github.horvathandris.localisation.L10nMessageSourceResolvable;
import javax.annotation.processing.Generated;

@Generated(
  value = "io.github.horvathandris.localisation.generator.SpringJavaGenerator",
  date = "2025-08-11T15:25:06.066379+01:00"
)
public final class L10n {
  private  L10n() {
  }
  public static final class Some {
    private  Some() {
    }
    public static final class Example {
      private  Example() {
      }
      /**
       * This is a message with {0} argument
       */
      public static L10nMessageSourceResolvable message(final String first) {
        return new L10nMessageSourceResolvable("some.example.message", "This is a message with {0} argument", first);
      }
    }
  }
}
```

