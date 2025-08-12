package io.github.horvathandris.localisation

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull

class L10nPluginTest {

    @Test
    fun `plugin registers task`() {
        // given
        val project = ProjectBuilder.builder().build()

        // when
        project.plugins.apply("io.github.horvathandris.localisation")

        // then
        assertNotNull(project.tasks.findByName("generateL10nMessages"))
    }

}
