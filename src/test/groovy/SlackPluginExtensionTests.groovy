import com.mindera.gradle.slack.SlackPlugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import java.security.cert.Extension

import static org.fest.assertions.api.Assertions.assertThat


class SlackPluginExtensionTests {

  static Project createProjectWithPlugin() {
    Project project = ProjectBuilder.builder().build()
    project.pluginManager.apply(SlackPlugin)
    assertThat(project.plugins.hasPlugin(SlackPlugin)).isEqualTo(true)
    return project
  }

  static Extension slackExt(Project proj) {
    return proj.extensions.getByName("slack") as Extension
  }

  final String testString = "test string"

  @Test void titleBuilderShouldGenerateStringFromClosure() {

    def project = createProjectWithPlugin()

    def closureThatBuildsAString = { testString }

    assertThat(closureThatBuildsAString()).isEqualTo(testString)

    project.slack {
      titleBuilder closureThatBuildsAString
    }

    def slackExt = slackExt(project)
    assertThat(slackExt.titleBuilder.call()).isEqualTo(testString)

  }

  @Test void titleBuilderShouldReturnTitleIfNotSetExplicitly() {

    def project = createProjectWithPlugin()

    def slackExt = slackExt(project)
    assertThat(slackExt.titleBuilder.call()).isEqualTo("")

    project.slack {
      title testString
    }

    assertThat(slackExt.titleBuilder.call()).isEqualTo(testString)
  }
}