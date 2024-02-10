package titan.ide.config;

import java.io.File;
import titan.ide.util.StringUtils;

/**
 * .
 *
 * @author tian wei jun
 */
public class ProjectIdeConfig {
  public static final String PROJECT_TITAN_IDE_CONFIG_FILE_NAME = "projectTitanIdeConfig.json";

  public String fileEncoding = "";

  public static boolean isProjectIdeConfigFile(File file) {
    return file.isFile() && PROJECT_TITAN_IDE_CONFIG_FILE_NAME.equals(file.getName());
  }

  public void beOverrode(ProjectIdeConfig projectIdeConfig) {
    if (StringUtils.isNotBlank(projectIdeConfig.fileEncoding)) {
      this.fileEncoding = projectIdeConfig.fileEncoding;
    }
  }
}
