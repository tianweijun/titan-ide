package titan.ide.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import titan.ide.context.IdeContext;
import titan.ide.exception.IdeRuntimeException;
import titan.json.Json;

/**
 * .
 *
 * @author tian wei jun
 */
public class ProjectIdeConfigBuilder {

  public ProjectIdeConfig build() {
    return new ProjectIdeConfig();
  }

  public ProjectIdeConfig build(File projectIdeConfigFile) {
    ProjectIdeConfig projectIdeConfig = null;
    try (FileInputStream projectIdeConfigInputStream = new FileInputStream(projectIdeConfigFile)) {
      projectIdeConfig =
          Json.fromJson(
              projectIdeConfigInputStream, ProjectIdeConfig.class, JsonConfig.JSON_ENCODING);
    } catch (IOException e) {
      throw new IdeRuntimeException(e);
    }
    IdeContext ideContext = IdeContext.get();
    ideContext.projectIdeConfig.beOverrode(projectIdeConfig);
    return ideContext.projectIdeConfig;
  }
}
