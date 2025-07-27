package dev.horvathandris.localisation

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull

class L10nPluginTest {

    @Test
    fun `plugin registers task`() {
        // given
        val project = ProjectBuilder.builder().build()

        // when
        project.plugins.apply("dev.horvathandris.localisation")

        // then
        assertNotNull(project.tasks.findByName("generateTranslationKeys"))
    }

}
