package io.github.horvathandris.localisation

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class L10nPluginFunctionalTest {

    @field:TempDir
    lateinit var projectDir: File

    private val buildFile by lazy { projectDir.resolve("build.gradle") }
    private val settingsFile by lazy { projectDir.resolve("settings.gradle") }
    private val messageBundleFile by lazy { projectDir.resolve("src/main/resources/messages.properties") }

    @Test
    fun `can run task`() {
        // given
        settingsFile.writeText("")
        buildFile.writeText("""
            plugins {
                id("java")
                id("io.github.horvathandris.localisation")
            }
            
            generateL10nMessages {
                messageBundleFile = file("src/main/resources/messages.properties")
                packageName = "io.github.horvathandris.something"
            }
        """.trimIndent())

        val sourceMessages = File(this.javaClass.classLoader.getResource("messages.properties")!!.toURI())
        sourceMessages.copyTo(messageBundleFile)

        // when
        GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withArguments("generateL10nMessages")
            .withProjectDir(projectDir)
            .withDebug(true)
            .build()

        // then
        val output = projectDir.resolve("build/generated/sources/l10n/main/java/io/github/horvathandris/something/L10n.java")
        assertTrue(output.exists())
        assertEquals(
            """
                package io.github.horvathandris.something;

                import javax.annotation.processing.Generated;
                
                @Generated("io.github.horvathandris.localisation.generator.SimpleJavaGenerator")
                public final class L10n {
                
                    private L10n() {}
                
                    public static final class Test {
                
                        private Test() {}
                
                        public static final class Message {
                
                            private Message() {}
                
                            /**
                             * Test message title with {0} and {1}
                             */
                            public static final String TITLE = "test.message.title";
                        }
                    }
                
                    public static final class Other {
                
                        private Other() {}
                
                        public static final class Message {
                
                            private Message() {}
                
                            /**
                             * Other message title
                             */
                            public static final String TITLE = "other.message.title";
                
                            /**
                             * Other message content
                             */
                            public static final String CONTENT = "other.message.content";
                        }
                    }
                
                    public static final class Some {
                
                        private Some() {}
                
                        public static final class Error {
                
                            private Error() {}
                
                            /**
                             * Some error title
                             */
                            public static final String TITLE = "some.error.title";
                
                            /**
                             * Some error content
                             */
                            public static final String CONTENT = "some.error.content";
                        }
                
                        public static final class Message {
                
                            private Message() {}
                
                            /**
                             * Some message title
                             */
                            public static final String TITLE = "some.message.title";
                
                            /**
                             * Some message content
                             */
                            public static final String CONTENT = "some.message.content";
                        }
                    }
                
                    public static final class Whitespace {
                
                        private Whitespace() {}
                
                        /**
                         * whitespace   test
                         */
                        public static final String TEST = "whitespace.test";
                
                        public static final class Trailing {
                
                            private Trailing() {}
                
                            /**
                             * trailing whitespace test       
                             */
                            public static final String TEST = "whitespace.trailing.test";
                        }
                    }
                
                    public static final class MessageCodeWithHyphens {
                
                        private MessageCodeWithHyphens() {}
                
                        /**
                         * Message code with hyphens
                         */
                        public static final String MESSAGE_CONTENT = "message-code-with-hyphens.message-content";
                    }
                }

            """.trimIndent(),
            output.readText(),
        )
    }
}
